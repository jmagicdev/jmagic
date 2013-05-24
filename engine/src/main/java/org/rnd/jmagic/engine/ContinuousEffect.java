package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.ContinuousEffectType.*;
import org.rnd.jmagic.engine.generators.Identity;

/**
 * Represents a continuous effect.
 */
public class ContinuousEffect extends Identified
{
	/**
	 * Represents an atomic part of a continuous effect. For example, the effect
	 * "this gets +1/+1 and becomes the color of your choice until end of turn"
	 * is composed of two parts.
	 */
	public static class Part implements Cloneable
	{
		public java.util.Map<ContinuousEffectType.Parameter, SetGenerator> parameters;
		public final ContinuousEffectType type;

		/**
		 * Constructs a continuous effect part that does nothing.
		 * 
		 * @param type What kind of effect to create.
		 */
		public Part(ContinuousEffectType type)
		{
			this.parameters = new java.util.HashMap<ContinuousEffectType.Parameter, SetGenerator>();
			this.type = type;
		}

		/**
		 * Applies this effect part.
		 * 
		 * @param game The game in which to apply this effect part.
		 * @param object The object to resolve generators with, or null if there
		 * is no applicable object.
		 * @param effect The effect of which this is a piece.
		 * @return The set of objects this effect acted on.
		 */
		public Set apply(Game game, Identified object, ContinuousEffect effect)
		{
			return this.apply(game.actualState, object, effect);
		}

		/**
		 * Applies this effect part.
		 * 
		 * @param object The object to resolve generators with, or null if there
		 * is no applicable object.
		 * @param effect The effect of which this is a piece.
		 * @return The set of objects this effect acted on.
		 */
		public Set apply(GameState state, Identified object, ContinuousEffect effect)
		{
			java.util.Map<ContinuousEffectType.Parameter, Set> parametersNow = new java.util.HashMap<ContinuousEffectType.Parameter, Set>();

			for(ContinuousEffectType.Parameter parameter: this.parameters.keySet())
			{
				// We assume that, if a parameter evaluation contains one
				// EventPattern, it contains only EventPatterns. Those
				// EventPatterns need to be solidified at this point in time to
				// prevent nasty things related to This/null.
				Set parameterNow = this.parameters.get(parameter).evaluate(state, object);
				if(parameterNow.getOne(EventPattern.class) == null)
					parametersNow.put(parameter, parameterNow);
				else
				{
					Set solidifiedParameter = new Set();
					for(EventPattern ep: parameterNow.getAll(EventPattern.class))
						solidifiedParameter.add(new EventPattern.WithContext(ep, object));
					parametersNow.put(parameter, solidifiedParameter);
				}

			}

			Identity previouslyAffectedObjects = effect.getPreviouslyAffectedObjects();
			Parameter affectedParameter = this.type.affects();

			// If the continuous effect part has an affected parameter, replace
			// it with the affected objects of the previously applied parts (if
			// any).
			Set affectedParameterObjects = parametersNow.get(affectedParameter);
			if(affectedParameter != null)
			{
				if(previouslyAffectedObjects != null)
				{
					affectedParameterObjects = previouslyAffectedObjects.evaluate(state, object);
					parametersNow.put(affectedParameter, affectedParameterObjects);
				}
				else
				{
					// If this is the first part applied, then the affected
					// objects parameter needs to be cleaned of ghosts.
					java.util.Set<GameObject> ghostsToRemove = new java.util.HashSet<GameObject>();
					for(GameObject o: affectedParameterObjects.getAll(GameObject.class))
						if(o.isGhost())
							ghostsToRemove.add(o);
					affectedParameterObjects.removeAll(ghostsToRemove);
				}

				// Clone any identifieds being modified and replace them
				// in parametersNow
				java.util.Set<Identified> originalsToRemove = new java.util.HashSet<Identified>();
				java.util.Set<Identified> clonesToAdd = new java.util.HashSet<Identified>();
				for(Identified i: affectedParameterObjects.getAll(Identified.class))
				{
					originalsToRemove.add(i);
					clonesToAdd.add(state.copyForEditing(i));
				}
				affectedParameterObjects.removeAll(originalsToRemove);
				affectedParameterObjects.addAll(clonesToAdd);

				// 400.7f If an effect grants a nonland card an ability that
				// allows it to be cast, that ability will continue to apply to
				// the new object that card became after it moved to the stack
				// as a result of being cast this way.
				if(this.type.layer() == ContinuousEffectType.Layer.ABILITY_ADD_OR_REMOVE)
				{
					for(GameObject onStack: state.stack().objects)
					{
						if(onStack.castAction == null)
							continue;
						if(onStack.castAction.sourceID == 0)
							continue;
						Identified actionSource = state.get(onStack.castAction.sourceID);
						if(!actionSource.isKeyword())
							continue;
						if(((Keyword)actionSource).grantedByID != effect.ID)
							continue;

						onStack = state.copyForEditing(onStack);
						affectedParameterObjects.add(onStack);
					}
				}
			}

			this.type.apply(state, effect, parametersNow);

			if(affectedParameter != null)
			{
				Set ret = affectedParameterObjects;
				if(parametersNow.containsKey(Parameter.REMOVED_OBJECTS))
					ret.add(parametersNow.get(Parameter.REMOVED_OBJECTS));
				return ret;
			}
			return null;
		}

		/**
		 * Java-copies this part.
		 */
		@Override
		public Part clone()
		{
			try
			{
				Part ret = (Part)super.clone();
				ret.parameters = new java.util.HashMap<ContinuousEffectType.Parameter, SetGenerator>(this.parameters);
				return ret;
			}
			catch(CloneNotSupportedException ex)
			{
				throw new InternalError();
			}
		}
	}

	/**
	 * The parts that comprise this effect.
	 */
	public java.util.Set<Part> parts;

	/**
	 * The objects this continuous effect is acting on.
	 */
	private Identity affectedObjects;

	/**
	 * If the source of this effect is a static ability, the ID of that static
	 * ability; otherwise -1
	 */
	public int staticSourceID;

	/**
	 * Constructs a continuous effect that does nothing.
	 * 
	 * @param state The game state in which this effect exists.
	 * @param name The text of this effect.
	 */
	ContinuousEffect(GameState state, String name)
	{
		super(state);

		this.setName(name);

		this.parts = new java.util.HashSet<Part>();
		this.staticSourceID = -1;
		this.affectedObjects = null;
	}

	/**
	 * Determines whether this effect applies in a given layer.
	 * 
	 * @param layer The layer to check.
	 * @return True if the effect applies in the given layer; false if it
	 * doesn't.
	 */
	public boolean appliesIn(ContinuousEffectType.Layer layer)
	{
		for(ContinuousEffect.Part p: this.parts)
			if(p.type.layer() == layer)
				return true;
		return false;
	}

	/**
	 * Determines whether this effect applies in a given layer.
	 * 
	 * @param layer The layer to check.
	 * @return True if the effect applies in the given layer; false if it
	 * doesn't.
	 */
	public boolean appliesIn(ContinuousEffectType.SubLayer layer)
	{
		for(ContinuousEffect.Part p: this.parts)
			if(p.type.subLayer() == layer)
				return true;
		return false;
	}

	/**
	 * Applies all the parts of this effect that apply in a given layer.
	 * 
	 * @param layer The layer to apply.
	 * @param object The object this effect came from.
	 */
	void apply(ContinuousEffectType.Layer layer, Identified object)
	{
		if(!this.canApply())
		{
			// if another effect causes this one to be able to apply later,
			// don't let it apply to any objects (see rule 613.5 quoted below)
			this.affectedObjects = null;
			return;
		}

		for(Part p: this.parts)
			if(p.type.layer() == layer)
			{
				// 613.5. ... If an effect starts to apply in one layer and/or
				// sublayer, it will continue to be applied to the same set of
				// objects in each other applicable layer and/or sublayer, even
				// if the ability generating the effect is removed during this
				// process.
				Set affected = p.apply(this.game, object, this);
				if(this.affectedObjects == null && affected != null)
					this.affectedObjects = Identity.instance(affected);
			}
	}

	/**
	 * Applies all the parts of this effect that apply in a given sublayer.
	 * 
	 * @param subLayer The sublayer to apply.
	 * @param object The object this effect came from.
	 */
	void apply(ContinuousEffectType.SubLayer subLayer, Identified object)
	{
		if(!this.canApply())
		{
			// if another effect causes this one to be able to apply later,
			// don't let it apply to any objects (see rule 613.5 quoted below)
			this.affectedObjects = null;
			return;
		}

		for(Part p: this.parts)
			if(p.type.subLayer() == subLayer)
			{
				// 613.5. ... If an effect starts to apply in one layer and/or
				// sublayer, it will continue to be applied to the same set of
				// objects in each other applicable layer and/or sublayer, even
				// if the ability generating the effect is removed during this
				// process.
				Set affected = p.apply(this.game, object, this);
				if(this.affectedObjects == null && affected != null)
					this.affectedObjects = Identity.instance(affected);
			}
	}

	/**
	 * Determines whether this effect can apply in the given state.
	 * FloatingContinuousEffect overrides this method to always return true.
	 */
	public boolean canApply()
	{
		// This effect can't apply if the ability generating it doesn't exist
		// anymore and it hasn't already started to apply.
		if(this.affectedObjects != null)
			return true;
		if(!this.state.containsIdentified(this.staticSourceID))
			return false;

		return this.getStaticSource().canApply();
	}

	/**
	 * Java-copies this effect.
	 */
	@Override
	public ContinuousEffect clone(GameState state)
	{
		ContinuousEffect ret = (ContinuousEffect)super.clone(state);
		ret.affectedObjects = null;
		ret.parts = new java.util.HashSet<Part>();
		for(Part p: this.parts)
			ret.parts.add(p.clone());
		return ret;
	}

	public Identity getPreviouslyAffectedObjects()
	{
		return this.affectedObjects;
	}

	/**
	 * Gets the source object for this effect. For effects from static
	 * abilities, that is the object that ability is printed on or granted to.
	 * For effects created by a resolving spell or ability, it's considered to
	 * be that spell or ability. (FloatingContinuousEffect overrides this
	 * method.)
	 * 
	 * @return The source object for this effect.
	 */
	public Identified getSourceObject()
	{
		return this.state.get(this.getStaticSource().sourceID);
	}

	public StaticAbility getStaticSource()
	{
		return this.state.get(this.staticSourceID);
	}

	/**
	 * Determines the timestamp of this continuous effect.
	 * 
	 * 418.5f A continuous effect generated by a static ability has the same
	 * timestamp as the object the static ability is on, or the timestamp of the
	 * effect that created the ability, whichever is later.
	 * 
	 * {@link FloatingContinuousEffect} overrides this method to provide the
	 * time the effect was created.
	 * 
	 * @return The timestamp of this continuous effect.
	 */
	public int getTimestamp()
	{
		StaticAbility staticAbility = this.getStaticSource();
		Identified source = staticAbility.getSource(this.state);

		// A continuous effect generated by a static ability has the same
		// timestamp as the object the static ability is on, or ...
		int timestamp1;
		if(source.isPlayer())
			timestamp1 = -1;
		else
			timestamp1 = ((GameObject)source).getTimestamp();

		// ... the timestamp of the effect that created the ability, ...
		int timestamp2 = staticAbility.createdByTimestamp;

		// ... whichever is later.
		return Math.max(timestamp1, timestamp2);
	}

	/**
	 * Returns the part of this continuous effect that applies in the given
	 * layer. This function assumes that only one part of a continuous effect
	 * will apply in any given layer.
	 * 
	 * @param layer The layer to check.
	 * @return The part of this effect that applies in the given layer; null if
	 * there is no such part.
	 */
	public Part partInLayer(ContinuousEffectType.Layer layer)
	{
		for(Part part: this.parts)
			if(part.type.layer() == layer)
				return part;

		return null;
	}

	/**
	 * Returns the part of this continuous effect that applies in the given
	 * subLayer of the power and toughness layer.. This function assumes that
	 * only one part of a continuous effect will apply in any given layer.
	 * 
	 * @param subLayer The sublayer to check.
	 * @return The part of this effect that applies in the given sublayer; null
	 * if there is no such part.
	 */
	public Part partInSubLayer(ContinuousEffectType.SubLayer subLayer)
	{
		for(Part part: this.parts)
			if(part.type.layer() == ContinuousEffectType.Layer.POWER_AND_TOUGHNESS && part.type.subLayer() == subLayer)
				return part;

		return null;
	}
}
