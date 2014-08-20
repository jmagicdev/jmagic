package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class YouMayHaveThisEnterTheBattlefieldAsACopy
{
	/**
	 * @eparam CAUSE: what is creating the copy effect
	 * @eparam OBJECT: the object to create the copy effect on
	 * @eparam PLAYER: the player who will make the choice
	 * @eparam SOURCE: the objects to choose from for the copy
	 * @eparam ABILITY: abilities to add as part of the copy effect (if any)
	 * @eparam PREVENT: {@link Characteristics#Characteristic}s to not copy
	 * (optional parameter; default is none)
	 * @eparam TAPPED: if this is present, the permanent will be tapped
	 * @eparam TYPE: types/supertypes/subtypes to add as part of the copy effect
	 * (if any)
	 * @eparam RESULT: empty
	 */
	public static final EventType CLONE_COPY = new EventType("CLONE_COPY")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);
			GameObject placeCopyEffectOn = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

			PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(1, 1, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.COPY);
			chooseParameters.thisID = placeCopyEffectOn.ID;

			java.util.List<?> choice = chooser.sanitizeAndChoose(game.actualState, parameters.get(Parameter.SOURCE), chooseParameters);

			GameObject createCopyEffectFrom = Set.fromCollection(choice).getOne(GameObject.class);

			if(createCopyEffectFrom != null)
			{
				if(parameters.containsKey(Parameter.TAPPED))
				{
					java.util.Map<Parameter, Set> tap = new java.util.HashMap<Parameter, Set>();
					tap.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
					tap.put(EventType.Parameter.OBJECT, new Set(placeCopyEffectOn));
					createEvent(game, "Tap " + placeCopyEffectOn, TAP_PERMANENTS, tap).perform(event, false);
				}

				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
				part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(placeCopyEffectOn));
				part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, IdentifiedWithID.instance(createCopyEffectFrom.ID));
				if(parameters.containsKey(Parameter.ABILITY))
					part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.fromCollection(parameters.get(Parameter.ABILITY)));
				if(parameters.containsKey(Parameter.TYPE))
					part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.fromCollection(parameters.get(Parameter.TYPE)));
				if(parameters.containsKey(Parameter.PREVENT))
					part.parameters.put(ContinuousEffectType.Parameter.RETAIN, Identity.fromCollection(parameters.get(Parameter.PREVENT)));

				java.util.Map<Parameter, Set> effectParameters = new java.util.HashMap<Parameter, Set>();
				effectParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				effectParameters.put(EventType.Parameter.EFFECT, new Set(part));
				effectParameters.put(EventType.Parameter.EXPIRES, new Set(Empty.instance()));
				Event copy = createEvent(game, placeCopyEffectOn + " copies " + choice + ".", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, effectParameters);
				copy.perform(event, false);
			}

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class CloneEffect extends ZoneChangeReplacementEffect
	{
		public CloneEffect(Game game, YouMayHaveThisEnterTheBattlefieldAsACopy factory)
		{
			super(game, factory.name);

			this.addPattern(asThisEntersTheBattlefield());
			this.makeOptional(You.instance());

			SetGenerator replacedMove = this.replacedByThis();

			EventFactory copy = new EventFactory(CLONE_COPY, this.name);
			copy.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(replacedMove));
			copy.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(replacedMove));
			copy.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(replacedMove));
			copy.parameters.put(EventType.Parameter.SOURCE, factory.choices);
			if(factory.ability != null)
				copy.parameters.put(EventType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(factory.ability)));
			if(!(factory.retainedCharacteristics.isEmpty()))
				copy.parameters.put(EventType.Parameter.PREVENT, Identity.fromCollection(factory.retainedCharacteristics));
			if(null != factory.subTypes)
				copy.parameters.put(EventType.Parameter.TYPE, Identity.instance((Object[])factory.subTypes));
			if(factory.tapped)
				copy.parameters.put(EventType.Parameter.TAPPED, NonEmpty.instance());

			this.addEffect(copy);
		}

		@Override
		public boolean isCloneEffect()
		{
			return true;
		}
	}

	private static final class ETBAsACopy extends StaticAbility
	{
		private YouMayHaveThisEnterTheBattlefieldAsACopy factory;

		public ETBAsACopy(GameState state, YouMayHaveThisEnterTheBattlefieldAsACopy factory)
		{
			super(state, factory.name);
			this.addEffectPart(replacementEffectPart(new CloneEffect(state.game, factory)));
			this.canApply = NonEmpty.instance();
			this.factory = factory;
		}

		@Override
		public ETBAsACopy create(org.rnd.jmagic.engine.Game game)
		{
			return new ETBAsACopy(game.physicalState, this.factory);
		}
	}

	private Class<?> ability;
	private final SetGenerator choices;
	private String name;
	private java.util.List<Characteristics.Characteristic> retainedCharacteristics;
	private SubType[] subTypes;
	private boolean tapped;

	/**
	 * You may have [x] enter the battlefield as a copy of any [y].
	 * 
	 * @param choices The allowed choices for what to copy.
	 */
	public YouMayHaveThisEnterTheBattlefieldAsACopy(SetGenerator choices)
	{
		this.ability = null;
		this.choices = choices;
		this.name = null;
		this.retainedCharacteristics = new java.util.LinkedList<Characteristics.Characteristic>();
		this.subTypes = null;
		this.tapped = false;
	}

	/**
	 * Note: If you want the permanent to enter the battlefield tapped,
	 * {@link #tapped()} must be called before this is called so the name is
	 * generated correctly.
	 * 
	 * @param cardName The thing that will be changed to a copy of something
	 * else.
	 * @param choiceText "any creature on the battlefield", for example
	 */
	public YouMayHaveThisEnterTheBattlefieldAsACopy generateName(String cardName, String choiceText)
	{
		this.name = "You may have " + cardName + " enter the battlefield " + (this.tapped ? "tapped and " : "") + "as a copy of " + choiceText + ".";
		return this;
	}

	public StaticAbility getStaticAbility(GameState state)
	{
		if(null == this.name)
			throw new IllegalStateException("A name must be set before trying to construct a StaticAbility");
		return new ETBAsACopy(state, this);
	}

	public YouMayHaveThisEnterTheBattlefieldAsACopy retainPowerAndToughness()
	{
		this.retainedCharacteristics.add(Characteristics.Characteristic.POWER);
		this.retainedCharacteristics.add(Characteristics.Characteristic.TOUGHNESS);
		return this;
	}

	public YouMayHaveThisEnterTheBattlefieldAsACopy setAbility(Class<?> ability)
	{
		this.ability = ability;
		return this;
	}

	public YouMayHaveThisEnterTheBattlefieldAsACopy setAdditionalSubTypes(SubType... subTypes)
	{
		this.subTypes = subTypes;
		return this;
	}

	public YouMayHaveThisEnterTheBattlefieldAsACopy setName(String name)
	{
		this.name = name;
		return this;
	}

	/**
	 * If the player opts to copy something, the permanent will enter the
	 * battlefield tapped
	 */
	public YouMayHaveThisEnterTheBattlefieldAsACopy tapped()
	{
		this.tapped = true;
		return this;
	}
}