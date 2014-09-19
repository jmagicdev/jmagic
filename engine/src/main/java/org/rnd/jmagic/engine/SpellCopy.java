package org.rnd.jmagic.engine;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.generators.*;

/** Represents a copy of a spell. */
public final class SpellCopy extends GameObject implements Castable
{
	private Castable.Simple castableDelegate;

	/**
	 * @param state The state to create this spell copy in.
	 * @param name What name to give to the copy
	 */
	public SpellCopy(GameState state, String name)
	{
		super(state);

		this.setName(name);
		this.castableDelegate = new Castable.Simple(this);
	}

	@Override
	public void addCastablePermissionLocation(PlayPermission permission)
	{
		this.castableDelegate.addCastablePermissionLocation(permission);
	}

	@Override
	public void addCastablePermissionTiming(PlayPermission permission)
	{
		this.castableDelegate.addCastablePermissionTiming(permission);
	}

	@Override
	public SpellCopy clone(GameState state)
	{
		SpellCopy ret = (SpellCopy)super.clone(state);
		ret.castableDelegate = this.castableDelegate.clone();
		return ret;
	}

	/**
	 * Counters this spell.
	 *
	 * @param counterer What is countering this spell.
	 * @return The countered object.
	 */
	@Override
	public GameObject counterThisObject(Set counterer)
	{
		return this.counterThisObject(counterer, this.getOwner(this.game.physicalState).getGraveyard(this.game.physicalState));
	}

	@Override
	public GameObject counterThisObject(Set counterer, Zone counterTo)
	{
		SetGenerator thisObject = IdentifiedWithID.instance(this.ID);

		Event move = new Event(this.game.physicalState, "Put " + this + " in " + counterTo + ".", EventType.MOVE_OBJECTS);
		move.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		move.parameters.put(EventType.Parameter.TO, Identity.instance(counterTo));
		move.parameters.put(EventType.Parameter.OBJECT, thisObject);
		// required for "if ~ would be put into a graveyard..." to work
		move.setSource(this);
		move.perform(null, true);
		return NewObjectOf.instance(move.getResultGenerator()).evaluate(this.game.actualState, null).getOne(GameObject.class);
	}

	@Override
	public SpellCopy create(Game game)
	{
		return new SpellCopy(game.physicalState, this.getName());
	}

	/**
	 * @return The converted mana cost of this spell.
	 */
	@Override
	public int[] getConvertedManaCost()
	{
		return java.util.Arrays.stream(this.getManaCost()).mapToInt(t -> null == t ? 0 : t.converted()).toArray();
	}

	@Override
	public java.util.List<CastSpellAction> getCastActions(GameState state, Player who)
	{
		return this.castableDelegate.getCastActions(state, who, this);
	}

	/**
	 * @return Whether this object is a spell (that is, whether it's on the
	 * stack). It should only not be a spell for very short periods of time;
	 * most of these times, state-based actions will make this object cease to
	 * exist.
	 */
	@Override
	public boolean isSpell()
	{
		return (this.zoneID == this.game.actualState.stack().ID);
	}

	/** return True. */
	@Override
	public boolean isSpellCopy()
	{
		return true;
	}

	/**
	 * Puts this spell on the stack.
	 *
	 * @return The spell on the stack.
	 */
	@Override
	public GameObject putOnStack(Player controller, java.util.Set<Integer> characteristicsIndices)
	{
		SetGenerator thisObject = IdentifiedWithID.instance(this.ID);

		Event stackEvent = new Event(this.game.physicalState, "Put " + this + " on the stack.", EventType.PUT_IN_CONTROLLED_ZONE);
		stackEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		stackEvent.parameters.put(EventType.Parameter.CONTROLLER, Identity.instance(controller));
		stackEvent.parameters.put(EventType.Parameter.ZONE, Stack.instance());
		stackEvent.parameters.put(EventType.Parameter.OBJECT, thisObject);
		stackEvent.parameters.put(EventType.Parameter.EFFECT, Identity.fromCollection(characteristicsIndices));
		stackEvent.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));

		stackEvent.perform(null, true);
		GameObject onStack = this.game.actualState.stack().objects.get(0);

		// This isn't in the rules, but you need it for casting a copy of a
		// spell to work
		java.util.Collection<ContinuousEffect.Part> partsToModify = new java.util.LinkedList<ContinuousEffect.Part>();
		for(FloatingContinuousEffect effect: this.game.physicalState.floatingEffects)
			for(ContinuousEffect.Part part: effect.parts)
			{
				if(part.type.layer() == ContinuousEffectType.Layer.RULE_CHANGE || part.type.affects() == null)
					continue;

				Set affectedObjects = part.parameters.get(part.type.affects()).evaluate(this.game, null);
				if(affectedObjects.contains(this))
					partsToModify.add(part);
			}

		for(ContinuousEffect.Part part: partsToModify)
		{
			SetGenerator affectedObjects = part.parameters.get(part.type.affects());
			SetGenerator newAffectedObjects = Union.instance(IdentifiedWithID.instance(onStack.ID), affectedObjects);
			part.parameters.put(part.type.affects(), newAffectedObjects);
		}

		this.game.refreshActualState();

		return this.game.actualState.get(onStack.ID);
	}

	/** Resolves this spell. */
	@Override
	public void resolve()
	{
		this.followInstructions();

		SetGenerator thisObject = IdentifiedWithID.instance(this.ID);

		Zone graveyard = ((SpellCopy)this.getActual()).getOwner(this.game.physicalState).getGraveyard(this.game.physicalState);
		Event putIntoGraveyard = new Event(this.game.physicalState, "Put " + this + " in " + graveyard + ".", EventType.MOVE_OBJECTS);
		putIntoGraveyard.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		putIntoGraveyard.parameters.put(EventType.Parameter.TO, Identity.instance(graveyard));
		putIntoGraveyard.parameters.put(EventType.Parameter.OBJECT, thisObject);
		// required for "if ~ would be put into a graveyard..." to work
		putIntoGraveyard.setSource(this);
		putIntoGraveyard.perform(null, true);
	}

	@Override
	public void selectCharacteristics(int characteristicsIndex)
	{
		// physical versions of spell copies don't have any characteristics to
		// select, so just tell the copy effect which side to copy.
		this.characteristicsSelection = characteristicsIndex;
	}

	@Override
	void setManaCost(ManaPool manaCost)
	{
		super.setManaCost(manaCost);
		this.setColors();
	}
}
