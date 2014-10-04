package org.rnd.jmagic;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public class Convenience
{
	/**
	 * Note a value for use later.
	 * 
	 * @eparam OBJECT: What value to store
	 * @eparam RESULT: The stored value
	 */
	public static final EventType NOTE = new EventType("NOTE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(parameters.get(Parameter.OBJECT));
			return true;
		}
	};

	/**
	 * Choose an object (or anything else) at random.
	 * 
	 * @eparam OBJECT: the potential choices
	 * @eparam NUMBER: the number of things to choose [optional; default is 1]
	 * @eparam RESULT: the chosen objects
	 */
	public static final EventType RANDOM = new EventType("RANDOM")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.LinkedList<Object> choices = new java.util.LinkedList<Object>(parameters.get(Parameter.OBJECT));
			Set ret = new Set();

			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));

			for(int i = 0; i < number && !choices.isEmpty(); i++)
			{
				java.util.Collections.shuffle(choices);
				ret.add(choices.pop());
			}

			event.setResult(ret);
			return ret.size() == number;
		}
	};

	public static final Set NEGATIVE_ONE = new Set.Unmodifiable(-1);
	public static final Set ZERO = new Set.Unmodifiable(0);
	public static final Set ONE = new Set.Unmodifiable(1);

	private static final class NegativeOne extends SetGenerator
	{
		private static final SetGenerator _instance = new NegativeOne();

		private NegativeOne()
		{
			// singleton generator
		}

		public static SetGenerator instance()
		{
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			return NEGATIVE_ONE;
		}
	}

	private static final class Zero extends SetGenerator
	{
		private static final SetGenerator _instance = new Zero();

		private Zero()
		{
			// singleton generator
		}

		public static SetGenerator instance()
		{
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			return ZERO;
		}
	}

	private static final class One extends SetGenerator
	{
		private static final SetGenerator _instance = new One();

		private One()
		{
			// singleton generator
		}

		public static SetGenerator instance()
		{
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			return ONE;
		}
	}

	/**
	 * This will generate the continuous effect parts necessary to 'animate' one
	 * or more objects. It will set the p/t, add (or set)
	 * types/subtypes/supertypes, and set colors if any are given.
	 */
	public static class Animator
	{
		private final SetGenerator target;
		private final SetGenerator power;
		private final SetGenerator toughness;

		private final java.util.Set<Class<?>> abilities;
		private final java.util.Set<Color> colors;
		private boolean loseAllAbilities;
		private boolean setColor;
		private final java.util.Set<SubType> subTypes;
		private final java.util.Set<SubType> subTypeRemoval;
		private final java.util.Set<SuperType> superTypes;
		private final java.util.Set<Type> types;

		private ContinuousEffectType animationMode;

		/**
		 * Fair warning: dont use Animator for creatures with a CDA for their
		 * p/t. Animator will still create a p/t setting effect, even if it's
		 * 0/0.
		 */
		public Animator(SetGenerator target, int power, int toughness)
		{
			this(target, numberGenerator(power), numberGenerator(toughness));
		}

		/**
		 * Fair warning: dont use Animator for creatures with a CDA for their
		 * p/t. Animator will still create a p/t setting effect, even if it's
		 * 0/0.
		 */
		public Animator(SetGenerator target, SetGenerator power, SetGenerator toughness)
		{
			this.target = target;
			this.power = power;
			this.toughness = toughness;

			this.animationMode = ContinuousEffectType.ADD_TYPES;
			this.abilities = new java.util.HashSet<Class<?>>();
			this.colors = java.util.EnumSet.noneOf(Color.class);
			this.loseAllAbilities = false;
			this.setColor = false;
			this.subTypes = java.util.EnumSet.noneOf(SubType.class);
			this.subTypeRemoval = java.util.EnumSet.noneOf(SubType.class);
			this.superTypes = java.util.EnumSet.noneOf(SuperType.class);
			this.types = java.util.EnumSet.of(Type.CREATURE);
		}

		public void addAbility(Class<?> ability)
		{
			this.abilities.add(ability);
		}

		/**
		 * Calling this will cause the object to lose colors it already had (if
		 * any).
		 */
		public void addColor(Color color)
		{
			this.colors.add(color);
			this.setColor = true;
		}

		public void addSubType(SubType subType)
		{
			this.subTypes.add(subType);
		}

		public void addSubTypes(java.util.Set<SubType> subTypes)
		{
			this.subTypes.addAll(subTypes);
		}

		public void addSuperType(SuperType superType)
		{
			this.superTypes.add(superType);
		}

		/** You don't need to call this for {@link Type#CREATURE}. */
		public void addType(Type type)
		{
			this.types.add(type);
		}

		public void colorless()
		{
			this.colors.clear();
			this.setColor = true;
		}

		public ContinuousEffect.Part[] getParts()
		{
			java.util.ArrayList<ContinuousEffect.Part> parts = new java.util.ArrayList<ContinuousEffect.Part>();

			parts.add(setPowerAndToughness(this.target, this.power, this.toughness));

			if(this.setColor)
			{
				ContinuousEffect.Part colorPart = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
				colorPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, this.target);
				colorPart.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.fromCollection(this.colors));
				parts.add(colorPart);
			}

			Set types = Set.fromCollection(this.types);
			types.addAll(this.subTypes);
			types.addAll(this.superTypes);
			ContinuousEffect.Part typesPart = new ContinuousEffect.Part(this.animationMode);
			typesPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, this.target);
			typesPart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.fromCollection(types));
			parts.add(typesPart);

			if(!this.subTypeRemoval.isEmpty())
			{
				ContinuousEffect.Part removeSubTypes = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
				removeSubTypes.parameters.put(ContinuousEffectType.Parameter.OBJECT, this.target);
				removeSubTypes.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.fromCollection(this.subTypeRemoval));
				parts.add(removeSubTypes);
			}

			if(!this.abilities.isEmpty())
			{
				java.util.Set<AbilityFactory> factories = new java.util.HashSet<AbilityFactory>();
				for(Class<?> clazz: this.abilities)
				{
					factories.add(new SimpleAbilityFactory(clazz));
				}

				ContinuousEffect.Part abilitiesPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
				abilitiesPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, this.target);
				abilitiesPart.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.fromCollection(factories));
				parts.add(abilitiesPart);
			}

			if(this.loseAllAbilities)
			{
				ContinuousEffect.Part humblePart = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
				humblePart.parameters.put(ContinuousEffectType.Parameter.OBJECT, this.target);
				parts.add(humblePart);
			}

			return parts.toArray(new ContinuousEffect.Part[0]);
		}

		public void losesAllAbilities()
		{
			this.loseAllAbilities = true;
		}

		/**
		 * Call this method if `It's still a land` or `in addition to its other
		 * types` (for example) is NOT in the effect.
		 */
		public void removeOldTypes()
		{
			this.animationMode = ContinuousEffectType.SET_TYPES;
		}

		/**
		 * call this method if `It's no loner an equipment` (for example)
		 */
		public void removeOneSubType(SubType remove)
		{
			this.subTypeRemoval.add(remove);
		}
	}

	/**
	 * @eparam PLAYER: players to iterate over; will be reordered to APNAP
	 * order. [optional; default is all players]
	 * @eparam TARGET: A {@link DynamicEvaluation} to set to each {@link Player}
	 * in PLAYER, one at a time (requires double-generator idiom and should
	 * really be used in EFFECT, though not strictly required)
	 * @eparam EFFECT: An {@link EventFactory} to perform. Players will be given
	 * the choice to perform the event until someone chooses to or all of the
	 * players in PLAYER have been asked.
	 * @eparam RESULT: A {@link java.util.Map} from Player ID to a
	 * {@link Identity} representing the result of each EFFECT for that player.
	 * Use an {@link ForEachResult} to get the results out with little hassle.
	 */
	public static final EventType ANY_PLAYER_MAY = new EventType("ANY_PLAYER_MAY")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Map<Integer, Identity> resultMap = new java.util.HashMap<Integer, Identity>();

			Set players;
			if(parameters.containsKey(Parameter.PLAYER))
				players = parameters.get(Parameter.PLAYER);
			else
				players = Set.fromCollection(game.physicalState.players);
			java.util.List<Player> playersInOrder;
			// ew ew EEWWWWW...
			// TODO : this is x-10 hackery. fix this when ticket 381 is done.
			if(game.hasStarted())
				playersInOrder = game.physicalState.apnapOrder(players);
			else
			{
				playersInOrder = new java.util.LinkedList<Player>();
				playersInOrder.addAll(players.getAll(Player.class));
			}

			DynamicEvaluation playerGenerator = parameters.get(Parameter.TARGET).getOne(DynamicEvaluation.class);
			if(playerGenerator == null)
				throw new NullPointerException("FOR_EACH_PLAYER.TARGET didn't contain a DynamicEvaluation");

			EventFactory effect = parameters.get(Parameter.EFFECT).getOne(EventFactory.class);

			for(Player player: playersInOrder)
			{
				Event e = effect.createEvent(game, event.getSource());
				playerGenerator.setEvaluation(Identity.instance(player));
				PlayerInterface.ChooseParameters<Answer> chooseParameters = new PlayerInterface.ChooseParameters<Answer>(1, 1, new java.util.LinkedList<Answer>(Answer.mayChoices()), PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.MAY_EVENT);
				chooseParameters.thisID = e.ID;
				if(player.choose(chooseParameters).contains(Answer.YES))
				{
					if(e.perform(event, false))
					{
						resultMap.put(player.ID, Identity.fromCollection(e.getResult()));
						event.setResult(new Set(resultMap));
						playerGenerator.setEvaluation(null);
						return true;
					}
				}
			}

			event.setResult(new Set(resultMap));
			playerGenerator.setEvaluation(null);
			return false;
		}
	};

	/**
	 * A SetGenerator that can change at game time what it evaluates to.
	 * Currently used by {@link Convenience#FOR_EACH_PLAYER} and
	 * {@link Convenience#FOR_EACH} to change which player is being referenced.
	 */
	public static class DynamicEvaluation extends SetGenerator
	{
		public static DynamicEvaluation instance()
		{
			return new DynamicEvaluation();
		}

		private SetGenerator evaluation;

		private DynamicEvaluation()
		{
			this.evaluation = null;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			return this.evaluation.evaluate(state, thisObject);
		}

		public void setEvaluation(SetGenerator g)
		{
			this.evaluation = g;
		}
	}

	/**
	 * Evaluates to the union of a SetGenerator evaluated multiple times for
	 * each item from another SetGenerator.
	 */
	public static final class ForEach extends SetGenerator
	{
		public static SetGenerator instance(SetGenerator in, SetGenerator doWhat, DynamicEvaluation toChange)
		{
			return new ForEach(in, doWhat, toChange);
		}

		private SetGenerator doWhat;
		private SetGenerator in;
		private DynamicEvaluation toChange;

		private ForEach(SetGenerator in, SetGenerator doWhat, DynamicEvaluation toChange)
		{
			this.doWhat = doWhat;
			this.in = in;
			this.toChange = toChange;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			for(Object o: this.in.evaluate(state, thisObject).getAll(Identified.class))
			{
				this.toChange.setEvaluation(Identity.instance(o));
				ret.addAll(this.doWhat.evaluate(state, thisObject));
			}
			return ret;
		}
	}

	/**
	 * For getting the results of FOR_EACH and FOR_EACH_PLAYER events. Evaluates
	 * to all the objects in all the given players'/things' results.
	 */
	public static final class ForEachResult extends SetGenerator
	{
		public static SetGenerator instance(EventFactory effectFactory, SetGenerator players)
		{
			return new ForEachResult(EffectResult.instance(effectFactory), players);
		}

		private final SetGenerator resultMap;

		private final SetGenerator things;

		private ForEachResult(SetGenerator resultMap, SetGenerator things)
		{
			this.resultMap = resultMap;
			this.things = things;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set result = new Set();
			@SuppressWarnings("unchecked") java.util.Map<Integer, Identity> resultMap = this.resultMap.evaluate(state, thisObject).getOne(java.util.Map.class);
			for(Identified i: this.things.evaluate(state, thisObject).getAll(Identified.class))
				result.addAll(resultMap.get(i.ID).evaluate(state, thisObject));
			return result;
		}
	}

	private static class ImmutableEventPattern implements EventPattern
	{
		private final EventPattern pattern;

		public ImmutableEventPattern(EventPattern pattern)
		{
			this.pattern = pattern;
		}

		@Override
		public boolean looksBackInTime()
		{
			return this.pattern.looksBackInTime();
		}

		@Override
		public boolean match(Event event, Identified object, GameState state)
		{
			return this.pattern.match(event, object, state);
		}

		@Override
		public boolean matchesManaAbilities()
		{
			return this.pattern.matchesManaAbilities();
		}
	}

	private static class ImmutableZoneChangePattern implements ZoneChangePattern
	{
		private final ZoneChangePattern pattern;

		public ImmutableZoneChangePattern(ZoneChangePattern pattern)
		{
			this.pattern = pattern;
		}

		@Override
		public boolean looksBackInTime()
		{
			return this.pattern.looksBackInTime();
		}

		@Override
		public boolean match(ZoneChange change, Identified thisObject, GameState state)
		{
			return this.pattern.match(change, thisObject, state);
		}
	}

	private static class NoncombatDamagePattern implements DamagePattern
	{
		private final DamagePattern wrap;

		public NoncombatDamagePattern(DamagePattern wrap)
		{
			this.wrap = wrap;
		}

		@Override
		public java.util.Set<DamageAssignment.Batch> match(DamageAssignment.Batch damage, Identified thisObject, GameState state)
		{
			java.util.Set<DamageAssignment.Batch> ret = this.wrap.match(damage, thisObject, state);
			java.util.Iterator<DamageAssignment.Batch> batchIter = ret.iterator();
			while(batchIter.hasNext())
			{
				DamageAssignment.Batch batch = batchIter.next();
				java.util.Iterator<DamageAssignment> damageIter = batch.iterator();
				while(damageIter.hasNext())
				{
					DamageAssignment a = damageIter.next();
					if(a.isCombatDamage)
						damageIter.remove();
				}
				if(batch.isEmpty())
					batchIter.remove();
			}
			return ret;
		}
	}

	/**
	 * @eparam EVENT: the event to repeat
	 * @eparam EFFECT: the conditions under which to stop repeating it (requires
	 * the double-generator idiom)
	 * @eparam RESULT: empty
	 */
	public static final EventType REPEAT_THIS_PROCESS = new EventType("REPEAT_THIS_PROCESS")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			EventFactory toRepeat = parameters.get(Parameter.EVENT).getOne(EventFactory.class);
			toRepeat.createEvent(game, event.getSource()).perform(event, true);

			SetGenerator condition = parameters.get(Parameter.EFFECT).getOne(SetGenerator.class);
			if(condition == null)
				throw new UnsupportedOperationException("REPEAT_THIS_PROCESS.EFFECT didn't contain a SetGenerator!");

			while(condition.evaluate(game, event.getSource()).isEmpty())
				toRepeat.createEvent(game, event.getSource()).perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	private static final class RestrictedMana extends ManaSymbol
	{
		private static final long serialVersionUID = 1L;

		private final boolean spellsOnly;
		// TODO: This really shouldn't be transient, but a String isn't good
		// enough for clients to analyze
		private transient SetPattern type;

		public RestrictedMana(ManaType color, SetPattern type, boolean spellsOnly, GameObject source)
		{
			this(color, type, spellsOnly, source.ID);
		}

		private RestrictedMana(ManaType color, SetPattern type, boolean spellsOnly, int sourceID)
		{
			super(color);
			this.name = type.toString() + " " + color.toString();
			this.type = type;
			this.spellsOnly = spellsOnly;
			this.sourceID = sourceID;
		}

		@Override
		public RestrictedMana create()
		{
			if(this.colors.isEmpty())
				return new RestrictedMana(ManaType.COLORLESS, this.type, this.spellsOnly, this.sourceID);
			return new RestrictedMana(this.colors.iterator().next().getManaType(), this.type, this.spellsOnly, this.sourceID);
		}

		@Override
		public boolean pays(GameState state, ManaSymbol cost)
		{
			if(!super.pays(state, cost))
				return false;

			if(cost.sourceID == -1)
				return false;
			GameObject source = state.getByIDObject(cost.sourceID);

			// [kind] Spells
			if(source.isSpell() && this.type.match(state, state.get(this.sourceID), new Set(source)))
				return true;

			if(!this.spellsOnly && source.isActivatedAbility())
			{
				Identified sourcesSource = ((ActivatedAbility)source).getSource(state);

				// Activated abilities of [kind]
				if(sourcesSource != null && this.type.match(state, state.get(this.sourceID), new Set(sourcesSource)) && sourcesSource.isPermanent())
					return true;
			}
			return false;
		}

		@Override
		public String toString()
		{
			return "(" + this.name + ")";
		}
	}

	/**
	 * Evaluates to the number of Zuberas put into graveyards from the
	 * battlefield this turn. Requires the enclosed tracker.
	 */
	public static final class ZuberasPutIntoGraveyardsFromBattlefieldThisTurn extends SetGenerator
	{
		public static final class Tracker extends MaximumPerPlayer.GameObjectsThisTurnCounter
		{
			@Override
			protected boolean match(GameState state, Event event)
			{
				if(event.type != EventType.MOVE_BATCH)
					return false;

				for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
				{
					if(state.battlefield().ID != change.sourceZoneID)
						continue;

					if(!state.<Zone>get(change.destinationZoneID).isGraveyard())
						continue;

					GameObject object = state.get(change.newObjectID);
					if(!object.getSubTypes().contains(SubType.ZUBERA))
						continue;

					return true;
				}
				return false;
			}
		}

		private static SetGenerator _instance = null;

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new ZuberasPutIntoGraveyardsFromBattlefieldThisTurn();
			return _instance;
		}

		private ZuberasPutIntoGraveyardsFromBattlefieldThisTurn()
		{
			// singleton
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			java.util.Map<Integer, Integer> flagValue = state.getTracker(Tracker.class).getValue(state);
			return new Set(Sum.get(Set.fromCollection(flagValue.values())));
		}
	}

	public static final SetGenerator ABILITY_SOURCE_IS_IN_GRAVEYARD = Intersect.instance(AbilitySource.instance(This.instance()), InZone.instance(GraveyardOf.instance(Players.instance())));

	public static final SetGenerator ABILITY_SOURCE_OF_THIS = AbilitySource.instance(This.instance());

	/**
	 * Add [mana] to your mana pool. Spend this mana only to cast [kind] spells
	 * or activate abilities of [kind] permanents.
	 * 
	 * @eparam SOURCE: the object creating the mana
	 * @eparam PLAYER: the player choosing the color and getting the mana
	 * @eparam TYPE: a SetPattern describing [kind] in the above text. this
	 * parameter should be composed of patterns that are not affected by text
	 * change effects.
	 * @eparam PERMANENT: if present, "Spend this mana only to cast [kind]
	 * spells"; activating abilities with the mana will not be allowed.
	 * @eparam MANA: the colors this mana is allowed to be (the player will
	 * choose a color;
	 * {@link org.rnd.jmagic.engine.ManaSymbol.ManaType#COLORLESS} for
	 * colorless.)
	 * @eparam NUMBER: how much mana to add (integer only) [optional; default is
	 * 1]
	 * @eparam RESULT: the result of ADD_MANA
	 */
	public static final EventType ADD_RESTRICTED_MANA = new EventType("ADD_RESTRICTED_MANA")
	{
		@Override
		public boolean addsMana()
		{
			return true;
		}

		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set source = parameters.get(EventType.Parameter.SOURCE);
			int sourceID = source.getOne(GameObject.class).ID;
			java.util.Set<Color> colors = parameters.get(Parameter.MANA).getAll(Color.class);
			ManaSymbol.ManaType type = ManaSymbol.ManaType.COLORLESS;
			if(!colors.isEmpty())
			{
				if(colors.size() > 1)
					type = parameters.get(EventType.Parameter.PLAYER).getOne(Player.class).chooseColor(colors, sourceID).getManaType();
				else
					type = colors.iterator().next().getManaType();
			}

			SetPattern allowed = parameters.get(EventType.Parameter.TYPE).getOne(SetPattern.class);
			GameObject sourceObject = source.getOne(GameObject.class);
			allowed.freeze(game.actualState, sourceObject);
			Set mana = new Set(new RestrictedMana(type, allowed, parameters.containsKey(Parameter.PERMANENT), sourceObject));

			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = parameters.get(Parameter.NUMBER).getOne(Integer.class);

			java.util.Map<EventType.Parameter, Set> manaParameters = new java.util.HashMap<EventType.Parameter, Set>();
			manaParameters.put(EventType.Parameter.SOURCE, source);
			manaParameters.put(EventType.Parameter.MANA, mana);
			manaParameters.put(EventType.Parameter.NUMBER, new Set(number));
			manaParameters.put(EventType.Parameter.PLAYER, parameters.get(EventType.Parameter.PLAYER));
			Event manaEvent = createEvent(game, "Add " + org.rnd.util.NumberNames.get(number) + " " + mana + " to your mana pool.", EventType.ADD_MANA, manaParameters);
			boolean ret = manaEvent.perform(event, false);

			event.setResult(manaEvent.getResult());
			return ret;
		}
	};

	public static final SetGenerator ALLIES_YOU_CONTROL = Intersect.instance(HasSubType.instance(SubType.ALLY), ControlledBy.instance(You.instance()));

	public static final SetGenerator ALLY_CREATURES_YOU_CONTROL = Intersect.instance(ALLIES_YOU_CONTROL, HasType.instance(Type.CREATURE));
	private static ZoneChangePattern allyTriggerPattern = null;

	private static ZoneChangePattern asThisEntersTheBattlefieldPattern = null;

	private static EventPattern asThisIsTurnedFaceUpPattern;

	private static EventPattern atTheBeginningOfEachPlayersUpkeepPattern = null;

	private static EventPattern atTheBeginningOfEachUpkeepPattern = null;

	private static EventPattern atTheBeginningOfOpponentsUpkeepsPattern = null;

	private static EventPattern atTheBeginningOfTheEndStepPattern = null;

	private static EventPattern atTheBeginningOfYourEndStepPattern = null;

	private static EventPattern atTheBeginningOfYourDrawStepPattern = null;

	private static EventPattern atTheBeginningOfYourUpkeepPattern = null;

	private static EventPattern atEndOfCombatPattern = null;

	private static EventPattern battalionPattern = null;

	/**
	 * Exile [object(s)], then return it/them to the battlefield under
	 * [player's] control.
	 * 
	 * @eparam CAUSE: the thing doing the blinking
	 * @eparam TARGET: the thing(s) being blinked
	 * @eparam PLAYER: the player getting it after it's blinked
	 * @eparam RESULT: empty
	 */
	public static final EventType BLINK = new EventType("BLINK")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);
			Set cause = parameters.get(Parameter.CAUSE);
			Set target = parameters.get(Parameter.TARGET);
			Set player = parameters.get(Parameter.PLAYER);

			java.util.Map<EventType.Parameter, Set> exileParameters = new java.util.HashMap<EventType.Parameter, Set>();
			exileParameters.put(EventType.Parameter.CAUSE, cause);
			exileParameters.put(EventType.Parameter.TO, new Set(game.actualState.exileZone()));
			exileParameters.put(EventType.Parameter.OBJECT, target);
			Event exileEvent = createEvent(game, "Exile " + target, EventType.MOVE_OBJECTS, exileParameters);
			boolean ret = exileEvent.perform(event, true);

			if(ret)
			{
				java.util.Set<ZoneChange> zoneChanges = exileEvent.getResult().getAll(ZoneChange.class);
				Set cardsToReturn = new Set();
				for(ZoneChange zoneChange: zoneChanges)
					cardsToReturn.add(game.actualState.get(zoneChange.newObjectID));

				java.util.Map<EventType.Parameter, Set> returnParameters = new java.util.HashMap<EventType.Parameter, Set>();
				returnParameters.put(EventType.Parameter.CAUSE, cause);
				returnParameters.put(EventType.Parameter.CONTROLLER, player);
				returnParameters.put(EventType.Parameter.OBJECT, cardsToReturn);
				Event returnEvent = createEvent(game, "Return it to the battlefield under " + "player" + "'s control.", EventType.PUT_ONTO_BATTLEFIELD, returnParameters);
				return returnEvent.perform(event, true);
			}

			return false;
		}
	};

	private static ZoneChangePattern constellationPattern = null;

	public static final SetGenerator CREATURES_AND_PLAYERS = Union.instance(Players.instance(), CreaturePermanents.instance());

	public static final SetGenerator CREATURES_YOU_CONTROL = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance()));

	public static final SetGenerator CREATURES_YOU_CONTROL_WITH_PLUS_ONE_COUNTER = Intersect.instance(CREATURES_YOU_CONTROL, HasCounterOfType.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));

	/**
	 * @eparam PLAYER: players to iterate over; will be reordered to APNAP
	 * order. [optional; default is all players]
	 * @eparam TARGET: A {@link DynamicEvaluation} to set to each {@link Player}
	 * in PLAYER, one at a time (requires double-generator idiom and should
	 * really be used in EFFECT, though not strictly required)
	 * @eparam EFFECT: An {@link EventFactory} to perform. If the event type
	 * specifies makeChoices, each player will make their choices before any of
	 * the events are performed.
	 * @eparam RESULT: A {@link java.util.Map} from Player ID to a
	 * {@link Identity} representing the result of each EFFECT for that player.
	 * Use an {@link ForEachResult} to get the results out with little hassle.
	 */
	public static final EventType FOR_EACH_PLAYER = new EventType("FOR_EACH_PLAYER")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Map<Integer, Identity> resultMap = new java.util.HashMap<Integer, Identity>();

			Set players;
			if(parameters.containsKey(Parameter.PLAYER))
				players = parameters.get(Parameter.PLAYER);
			else
				players = Set.fromCollection(game.physicalState.players);
			java.util.List<Player> playersInOrder;
			// ew ew EEWWWWW...
			// TODO : this is x-10 hackery. fix this when ticket 381 is done.
			if(game.hasStarted())
				playersInOrder = game.physicalState.apnapOrder(players);
			else
			{
				playersInOrder = new java.util.LinkedList<Player>();
				playersInOrder.addAll(players.getAll(Player.class));
			}

			DynamicEvaluation playerGenerator = parameters.get(Parameter.TARGET).getOne(DynamicEvaluation.class);
			if(playerGenerator == null)
				throw new NullPointerException("FOR_EACH_PLAYER.TARGET didn't contain a DynamicEvaluation");

			boolean status = false;
			EventFactory effect = parameters.get(Parameter.EFFECT).getOne(EventFactory.class);

			// keys are player IDs, values are those players' events' IDs
			java.util.Map<Integer, Integer> events = new java.util.HashMap<Integer, Integer>();
			for(Player player: playersInOrder)
			{
				playerGenerator.setEvaluation(Identity.instance(player));
				Event e = effect.createEvent(game, event.getSource());
				e.makeChoices(event);
				events.put(player.ID, e.ID);
			}

			for(Player player: playersInOrder)
			{
				playerGenerator.setEvaluation(Identity.instance(player));
				Event e = game.physicalState.get(events.get(player.ID));
				status = e.perform(event, false) || status;
				resultMap.put(player.ID, Identity.fromCollection(e.getResult()));
			}

			playerGenerator.setEvaluation(null);
			event.setResult(Identity.instance(resultMap));
			return status;
		}
	};

	/**
	 * @eparam OBJECT: {@link Identified}s to iterate over
	 * @eparam TARGET: A {@link DynamicEvaluation} to set to each thing in in
	 * OBJECT, one at a time (requires double-generator idiom and should really
	 * be used in EFFECT, though not strictly required)
	 * @eparam EFFECT: An {@link EventFactory} to perform. If the event type
	 * specifies makeChoices, each player will make their choices before any of
	 * the events are performed.
	 * @eparam RESULT: A {@link java.util.Map} from ID to an {@link Identity}
	 * representing the result of each EFFECT for that thing. Use an
	 * {@link ForEachResult} to get the results out with little hassle.
	 */
	public static final EventType FOR_EACH = new EventType("FOR_EACH")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Map<Integer, Identity> resultMap = new java.util.HashMap<Integer, Identity>();

			Set things;
			if(parameters.containsKey(Parameter.OBJECT))
				things = parameters.get(Parameter.OBJECT);
			else
				things = Set.fromCollection(game.physicalState.players);

			DynamicEvaluation thingGenerator = parameters.get(Parameter.TARGET).getOne(DynamicEvaluation.class);
			if(thingGenerator == null)
				throw new NullPointerException("FOR_EACH.TARGET didn't contain a DynamicEvaluation");

			boolean status = false;
			EventFactory effect = parameters.get(Parameter.EFFECT).getOne(EventFactory.class);

			// keys are player IDs, values are those players' events' IDs
			java.util.Map<Integer, Integer> events = new java.util.HashMap<Integer, Integer>();
			for(Identified i: things.getAll(Identified.class))
			{
				thingGenerator.setEvaluation(Identity.instance(i));
				Event e = effect.createEvent(game, event.getSource());
				e.makeChoices(event);
				events.put(i.ID, e.ID);
			}

			for(Identified i: things.getAll(Identified.class))
			{
				thingGenerator.setEvaluation(Identity.instance(i));
				Event e = game.physicalState.get(events.get(i.ID));
				status = e.perform(event, false) || status;
				resultMap.put(i.ID, Identity.fromCollection(e.getResult()));
			}

			thingGenerator.setEvaluation(null);
			event.setResult(Identity.instance(resultMap));
			return status;
		}
	};

	public static final PlayerInterface.ChooseReason LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM_REASON = new PlayerInterface.ChooseReason("{Convenience}", "Put a card into your hand.", false);

	/**
	 * s
	 * 
	 * @eparam CAUSE: the object causing this
	 * @eparam NUMBER: the number of cards to look at
	 * @eparam PLAYER: the player choosing
	 * @eparam ZONE: the library to look through
	 * @eparam TYPE: [optional] if specified, the kinds of cards you can choose
	 * @eparam RESULT: empty
	 */
	public static final EventType LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM = new EventType("LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int num = Sum.get(parameters.get(Parameter.NUMBER));
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Set playerSet = new Set(player);
			Zone library = parameters.get(Parameter.ZONE).getOne(Zone.class);
			Set librarySet = new Set(library);

			Set objects = new Set();
			if(num >= library.objects.size())
				objects.addAll(library.objects);
			else
				for(int i = 0; i < num; i++)
					objects.add(library.objects.get(i));

			Set cause = parameters.get(Parameter.CAUSE);

			// I dont want to deal with empty collections for choose
			if(!objects.isEmpty())
			{
				java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
				lookParameters.put(Parameter.CAUSE, cause);
				lookParameters.put(Parameter.OBJECT, Set.fromCollection(objects));
				lookParameters.put(Parameter.PLAYER, playerSet);
				Event lookEvent = createEvent(game, "Look at the top X cards of your library.", EventType.LOOK, lookParameters);
				lookEvent.perform(event, true);

				java.util.Set<GameObject> chooseable = objects.getAll(GameObject.class);
				if(parameters.containsKey(Parameter.TYPE))
					chooseable.retainAll(parameters.get(Parameter.TYPE));

				java.util.List<GameObject> choice = player.getActual().sanitizeAndChoose(game.actualState, 1, chooseable, PlayerInterface.ChoiceType.OBJECTS, LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM_REASON);
				objects.removeAll(choice);

				// These two events are not top level because they happen
				// simultaneously.
				// TODO : Should this be (yet another) separate event? Should we
				// make an event that can move different cards to different
				// zones at the same time?

				java.util.Map<Parameter, Set> handParameters = new java.util.HashMap<Parameter, Set>();
				handParameters.put(Parameter.CAUSE, cause);
				handParameters.put(Parameter.TO, new Set(player.getHand(game.actualState)));
				handParameters.put(Parameter.OBJECT, Set.fromCollection(choice));
				Event handEvent = createEvent(game, "Put one of them into your hand.", EventType.MOVE_OBJECTS, handParameters);
				handEvent.perform(event, false);

				java.util.Map<Parameter, Set> libraryParameters = new java.util.HashMap<Parameter, Set>();
				libraryParameters.put(Parameter.CAUSE, cause);
				libraryParameters.put(Parameter.TO, librarySet);
				libraryParameters.put(Parameter.OBJECT, objects);
				libraryParameters.put(Parameter.INDEX, NEGATIVE_ONE);
				Event libraryEvent = createEvent(game, "And the rest on the bottom of your library.", EventType.MOVE_OBJECTS, libraryParameters);
				libraryEvent.perform(event, false);
			}

			event.setResult(Empty.set);

			return true;
		}
	};

	private static EventPattern heroicPattern = null;

	private static EventPattern inspiredPattern = null;

	private static ZoneChangePattern landfallPattern = null;

	private static SetPattern landPermanents = null;

	private static SetPattern permanents = null;

	public static final PlayerInterface.ChooseReason PLAY_ANY_NUMBER_WITHOUT_PAYING_MANA_COSTS_REASON = new PlayerInterface.ChooseReason("Convenience", "Choose a card to play; choose no cards to finish playing cards.", true);

	/**
	 * @eparam CAUSE: what is causing the plays
	 * @eparam PLAYER: who will be doing the playing (must be only one)
	 * @eparam OBJECT: cards playable by this ability
	 * @eparam NUMBER: number of cards to play (optional; default is any number)
	 * @eparam RESULT: the spells on the stack that were cast / lands on the
	 * battlefield that were played
	 */
	public static final EventType PLAY_WITHOUT_PAYING_MANA_COSTS = new EventType("PLAY_ANY_NUMBER_WITHOUT_PAYING_MANA_COSTS")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// Object cause =
			// parameters.get(Parameter.CAUSE).getOne(Object.class);
			Set player = parameters.get(Parameter.PLAYER);
			Player you = player.getOne(Player.class);
			Set cards = parameters.get(Parameter.OBJECT);
			int number;
			if(parameters.containsKey(Parameter.NUMBER))
				number = parameters.get(Parameter.NUMBER).getOne(Integer.class);
			else
				number = -1;

			Set result = new Set();
			while(!cards.isEmpty() && ((-1 == number) || (result.size() < number)))
			{
				java.util.Map<GameObject, Event> events = new java.util.HashMap<GameObject, Event>();
				for(GameObject card: cards.getAll(GameObject.class))
				{
					java.util.Map<Parameter, Set> castParameters = new java.util.HashMap<Parameter, Set>();
					castParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
					castParameters.put(Parameter.PLAYER, player);
					castParameters.put(Parameter.ALTERNATE_COST, Empty.set);
					castParameters.put(Parameter.OBJECT, new Set(card));
					Event cast = createEvent(game, you + " casts " + card, PLAY_CARD, castParameters);
					if(cast.attempt(event))
						events.put(card, cast);
				}

				you = you.getActual();
				java.util.List<GameObject> choice = you.sanitizeAndChoose(game.actualState, 0, 1, events.keySet(), PlayerInterface.ChoiceType.OBJECTS, PLAY_ANY_NUMBER_WITHOUT_PAYING_MANA_COSTS_REASON);
				if(choice.isEmpty())
					break;

				Event cast = events.get(choice.iterator().next());
				if(cast.perform(event, true))
				{
					cards.removeAll(choice);
					result.addAll(cast.getResult());
				}
			}

			event.setResult(result);
			return true;
		}
	};

	/**
	 * Look at the top [n] cards of your library. You may reveal a [card type]
	 * card from among them and put that card into your hand. Put the rest on
	 * the bottom of your library in any order.
	 * 
	 * @eparam CAUSE: the cause of everything
	 * @eparam PLAYER: the player controlling the cause of everything as it
	 * resolves
	 * @eparam CARD: the top N cards of the library of the player controlling
	 * the cause of everything as it resolves
	 * @eparam TYPE: the type of cards to choose from when looking through the
	 * top N cards of the library of the player controlling the cause of
	 * everything as it resolves
	 * @eparam TO: The zone to put the other cards. [optional; default is
	 * player's library] Cannot be a controlled zone unless you feel like adding
	 * a controller parameter and such.
	 * @eparam INDEX: The position to insert the other cards in the TO zone
	 * [optional; default is -1 (the bottom)]
	 * @eparam RESULT: empty
	 */
	public static EventType PUT_ONE_FROM_TOP_N_OF_LIBRARY_INTO_HAND = new EventType("PUT_ONE_FROM_TOP_N_OF_LIBRARY_INTO_HAND")
	{

		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);

			Set you = parameters.get(Parameter.PLAYER);
			Set cards = parameters.get(Parameter.CARD);

			java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
			lookParameters.put(Parameter.CAUSE, cause);
			lookParameters.put(Parameter.PLAYER, you);
			lookParameters.put(Parameter.OBJECT, cards);
			createEvent(game, "Look at the top five cards of your library", LOOK, lookParameters).perform(event, true);

			Set filteredCards = new Set();
			Set filter = parameters.get(Parameter.TYPE);
			for(GameObject object: cards.getAll(GameObject.class))
				if(filter.contains(object))
					filteredCards.add(object);

			EventType.ParameterMap revealParameters = new EventType.ParameterMap();
			revealParameters.put(Parameter.CAUSE, Identity.fromCollection(cause));
			revealParameters.put(Parameter.PLAYER, Identity.fromCollection(you));
			revealParameters.put(Parameter.OBJECT, Identity.fromCollection(filteredCards));

			java.util.Map<Parameter, Set> mayParameters = new java.util.HashMap<Parameter, Set>();
			mayParameters.put(Parameter.PLAYER, you);
			mayParameters.put(Parameter.EVENT, new Set(new EventFactory(REVEAL_CHOICE, revealParameters, "Reveal a card from among them")));
			Set revealedCard = new Set();
			Event may = createEvent(game, "You may reveal a creature card from among them", PLAYER_MAY, mayParameters);
			if(may.perform(event, true))
				// there's only one of these
				for(Event reveal: may.children.keySet())
					revealedCard.addAll(reveal.getResult());

			Player player = you.getOne(Player.class).getActual();
			Set hand = new Set(player.getHand(game.actualState));

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.TO, hand);
			moveParameters.put(Parameter.OBJECT, revealedCard);
			createEvent(game, "Put that card into your hand", MOVE_OBJECTS, moveParameters).perform(event, true);

			Set putOnBottom = Set.fromCollection(cards);
			putOnBottom.removeAll(revealedCard);

			Set zone = parameters.containsKey(Parameter.TO) ? parameters.get(Parameter.TO) : new Set(player.getLibrary(game.actualState));
			Set index = parameters.containsKey(Parameter.INDEX) ? parameters.get(Parameter.INDEX) : NEGATIVE_ONE;

			java.util.Map<Parameter, Set> bottomParameters = new java.util.HashMap<Parameter, Set>();
			bottomParameters.put(Parameter.CAUSE, cause);
			bottomParameters.put(Parameter.TO, zone);
			bottomParameters.put(Parameter.INDEX, index);
			bottomParameters.put(Parameter.OBJECT, putOnBottom);
			createEvent(game, "Put the rest in " + zone + " in any order", MOVE_OBJECTS, bottomParameters).perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	/**
	 * @eparam CAUSE: what's causing cards to be moved
	 * @eparam PLAYER: whose cards they are (and who will choose)
	 * @eparam NUMBER: number to choose
	 * @eparam INDEX: the location in the library to put the cards (default: 1)
	 * @eparam REASON: the PlayerInterface.ChooseReason to use
	 * @eparam RESULT: empty
	 */
	public static final EventType PUT_INTO_LIBRARY_FROM_HAND_CHOICE = new EventType("PUT_ONTO_LIBRARY_FROM_HAND_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));
			if(numberOfCards < 0)
				numberOfCards = 0;

			Set cause = parameters.get(Parameter.CAUSE);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Zone hand = player.getHand(game.actualState);
			PlayerInterface.ChooseReason reason = parameters.get(Parameter.REASON).getOne(PlayerInterface.ChooseReason.class);

			// offer the choices to the player
			java.util.List<GameObject> putTheseOnTop = player.sanitizeAndChoose(game.actualState, numberOfCards, hand.objects, PlayerInterface.ChoiceType.OBJECTS, reason);

			// build the Set of objects to put back
			Set moveThese = Set.fromCollection(putTheseOnTop);

			Set index = parameters.containsKey(Parameter.INDEX) ? parameters.get(Parameter.INDEX) : ONE;

			// perform the put-into-library event
			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.TO, new Set(player.getLibrary(game.actualState)));
			moveParameters.put(Parameter.INDEX, index);
			moveParameters.put(Parameter.OBJECT, moveThese);
			Event putBack = createEvent(game, player + " puts " + moveThese + " into his/her library.", MOVE_OBJECTS, moveParameters);
			boolean ret = putBack.perform(event, false);

			event.setResult(Empty.set);
			return ret;
		}
	};

	public static final PlayerInterface.ChooseReason REVEAL_SOME_CARDS_AND_DISCARD_FORCE_REASON = new PlayerInterface.ChooseReason("Convenience", "Which card will target player discard?", true);

	/**
	 * Target player reveals [a number of cards]. You choose one of them. That
	 * player discards that card.
	 * 
	 * @eparam CAUSE: causing object or ability
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam NUMBER: number of cards to reveal
	 * @eparam TARGET: target player
	 */
	public static final EventType REVEAL_SOME_CARDS_AND_DISCARD_FORCE = new EventType("REVEAL_SOME_CARDS_AND_DISCARD_FORCE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player target = parameters.get(Parameter.TARGET).getOne(Player.class);
			Zone hand = target.getHand(game.actualState);
			int number = Math.min(Sum.get(parameters.get(Parameter.NUMBER)), hand.objects.size());

			Set cause = parameters.get(Parameter.CAUSE);
			java.util.List<GameObject> revealedCards = target.sanitizeAndChoose(game.actualState, number, hand.objects, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.REVEAL);

			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(Parameter.CAUSE, cause);
			revealParameters.put(Parameter.OBJECT, Set.fromCollection(revealedCards));
			Event reveal = createEvent(game, "Target player reveals a number of cards from his or her hand equal to the number of Allies you control", REVEAL, revealParameters);
			reveal.perform(event, true);

			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class).getActual();
			Set chosenCard = Set.fromCollection(you.sanitizeAndChoose(game.actualState, 1, revealedCards, PlayerInterface.ChoiceType.OBJECTS, REVEAL_SOME_CARDS_AND_DISCARD_FORCE_REASON));

			java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
			discardParameters.put(Parameter.CAUSE, new Set(cause.getOne(GameObject.class).getActual()));
			discardParameters.put(Parameter.CARD, chosenCard);
			Event discard = createEvent(game, "That player discards that card.", EventType.DISCARD_CARDS, discardParameters);
			discard.perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	/**
	 * Exile [x]. Return it to the battlefield under its owner's control at the
	 * beginning of the next end step.
	 * 
	 * @eparam CAUSE: the cause
	 * @eparam TARGET: the creature to slide
	 * @eparam TAPPED: (optional) if present, creature will return tapped.
	 * @eparam RESULT: empty
	 */
	public static final EventType SLIDE = new EventType("SLIDE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(EventType.Parameter.CAUSE);

			java.util.Map<EventType.Parameter, Set> rfgParameters = new java.util.HashMap<EventType.Parameter, Set>();
			rfgParameters.put(EventType.Parameter.CAUSE, cause);
			rfgParameters.put(EventType.Parameter.TO, new Set(game.actualState.exileZone()));
			rfgParameters.put(EventType.Parameter.OBJECT, parameters.get(EventType.Parameter.TARGET));
			Event rfgEvent = createEvent(game, "Exile target creature", EventType.MOVE_OBJECTS, rfgParameters);
			boolean ret = rfgEvent.perform(event, true);

			// Trigger event
			SimpleEventPattern eot = new SimpleEventPattern(EventType.BEGIN_STEP);
			eot.put(EventType.Parameter.STEP, EndStepOf.instance(Players.instance()));

			ZoneChange change = rfgEvent.getResult().getOne(ZoneChange.class);
			GameObject cardToReturn = game.actualState.get(change.newObjectID);

			// Trigger effect
			EventType.ParameterMap returnParameters = new EventType.ParameterMap();
			returnParameters.put(EventType.Parameter.CAUSE, Identity.fromCollection(cause));
			returnParameters.put(EventType.Parameter.CONTROLLER, Identity.instance(cardToReturn.getOwner(game.actualState)));
			returnParameters.put(EventType.Parameter.OBJECT, Identity.instance(cardToReturn));

			EventType slideBack = (parameters.containsKey(Parameter.TAPPED) ? EventType.PUT_ONTO_BATTLEFIELD_TAPPED : EventType.PUT_ONTO_BATTLEFIELD);

			// Delayed trigger parameter map
			java.util.Map<EventType.Parameter, Set> trigParameters = new java.util.HashMap<EventType.Parameter, Set>();
			trigParameters.put(EventType.Parameter.CAUSE, cause);
			trigParameters.put(EventType.Parameter.EVENT, new Set(eot));
			trigParameters.put(EventType.Parameter.EFFECT, new Set(new EventFactory(slideBack, returnParameters, "Return that creature to the battlefield under its owner's control.")));
			Event triggerEvent = createEvent(game, "At the beginning of the next end step, return that creature to the battlefield under its owner's control", EventType.CREATE_DELAYED_TRIGGER, trigParameters);
			ret = triggerEvent.perform(event, true) && ret;

			event.setResult(Empty.set);
			return ret;
		}
	};

	public static final SetGenerator SLIVER_CREATURES_YOU_CONTROL = Intersect.instance(HasSubType.instance(SubType.SLIVER), HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance()));

	private static SetPattern spellsAndAbilitiesPattern = null;

	private static EventPattern spiritcraftPattern = null;

	/**
	 * Note that this literally uses {@link This}, so it's not appropriate for
	 * non-static abilities. Use {@link #ABILITY_SOURCE_IS_IN_GRAVEYARD} instead
	 * for non-static abilities.
	 */
	public static final SetGenerator THIS_IS_IN_A_GRAVEYARD = Intersect.instance(This.instance(), InZone.instance(GraveyardOf.instance(Players.instance())));

	public static final SetGenerator THIS_IS_ON_THE_BATTLEFIELD = Intersect.instance(This.instance(), Permanents.instance());

	public static final SetGenerator THIS_IS_ON_THE_STACK = Intersect.instance(This.instance(), InZone.instance(Stack.instance()));

	private static ZoneChangePattern whenAnotherCreatureIsPutIntoAGraveyardFromTheBattlefieldPattern = null;

	private static ZoneChangePattern whenAPlayerCyclesACardPattern = null;

	private static EventPattern whenThisAttacksPattern = null;

	private static EventPattern whenThisBecomesMonstrosPattern = null;

	private static EventPattern whenThisBlocksPattern = null;

	private static ZoneChangePattern whenThisEntersTheBattlefieldPattern = null;

	private static ZoneChangePattern whenThisIsPutIntoAGraveyardFromTheBattlefieldPattern = null;

	private static ZoneChangePattern whenThisIsPutIntoYourGraveyardFromTheBattlefieldPattern = null;

	private static EventPattern whenThisIsTurnedFaceUpPattern = null;

	private static ZoneChangePattern whenThisLeavesTheBattlefieldPattern = null;

	private static EventPattern whenTimeCounterIsRemovedFromThisPattern = null;

	private static EventPattern whenYouCastANoncreatureSpellPattern = null;

	private static EventPattern whenYouCastASpellPattern = null;

	private static EventPattern whenYouCastASpellFromYourGraveyardPattern = null;

	private static EventPattern whenYouCastThisSpellPattern = null;

	private static ZoneChangePattern whenYouCycleThisPattern = null;

	/**
	 * @eparam PLAYER who's separating
	 * @eparam CARD what's being separated
	 * @eparam NUMBER how many piles there will be
	 * @eparam RESULT the piles
	 */
	private static final EventType SEPARATE_INTO_PILES = new EventType("SEPARATE_INTO_PILES")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			int number = parameters.get(Parameter.NUMBER).getOne(Integer.class);
			Set cards = parameters.get(Parameter.CARD);
			event.setResult(Set.fromCollection(player.separateIntoPiles(number, cards)));
			return true;
		}
	};

	/**
	 * @eparam EFFECT a {@link java.util.List} of {@link EventFactory}s, which
	 * will be performed in the order given.
	 * @eparam EVENT if present, events will be performed "simultaneously" (not
	 * top level).
	 * @eparam RESULT empty
	 */
	private static final EventType SEQUENCE = new EventType("SEQUENCE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set effects = parameters.get(Parameter.EFFECT);
			for(EventFactory e: effects.getAll(EventFactory.class))
				if(!(e.createEvent(game, event.getSource()).attempt(event)))
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean topLevel = !parameters.containsKey(Parameter.EVENT);
			boolean ret = true;
			@SuppressWarnings("unchecked") java.util.List<EventFactory> toPerform = parameters.get(Parameter.EFFECT).getOne(java.util.List.class);
			for(EventFactory factory: toPerform)
			{
				// Do not localize event.getSource out of this loop, because
				// this loop is liable to refresh the state, invalidating the
				// source each time it iterates.
				boolean performed = factory.createEvent(game, event.getSource()).perform(event, topLevel);
				ret = performed && ret;
			}

			event.setResult(Empty.set);
			return ret;
		}

	};

	public static ContinuousEffect.Part addAbilityToObject(SetGenerator who, AbilityFactory... factories)
	{
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, who);
		part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance((Object[])factories));
		return part;
	}

	public static ContinuousEffect.Part addAbilityToObject(SetGenerator who, Class<?>... abilities)
	{
		java.util.Set<AbilityFactory> factories = new java.util.HashSet<AbilityFactory>();
		for(Class<?> clazz: abilities)
		{
			factories.add(new SimpleAbilityFactory(clazz));
		}

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, who);
		part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.fromCollection(factories));
		return part;
	}

	public static EventFactory addAbilityUntilEndOfTurn(SetGenerator who, Class<? extends Identified> ability, String effectName)
	{
		return createFloatingEffect(effectName, addAbilityToObject(who, ability));
	}

	public static EventFactory addProtectionUntilEndOfTurn(SetGenerator who, SetGenerator colors, String effectName)
	{
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, who);
		part.parameters.put(ContinuousEffectType.Parameter.ABILITY, org.rnd.jmagic.abilities.keywords.Protection.FromColor.instance(colors));
		return createFloatingEffect(effectName, part);
	}

	public static EventFactory addManaToYourManaPoolFromAbility(String mana)
	{
		return addManaToYourManaPoolFromAbility(mana, "Add " + mana + " to your mana pool.");
	}

	public static EventFactory addManaToYourManaPoolFromAbility(String mana, String effectName)
	{
		return addManaToYourManaPoolFromAbility(Identity.fromCollection(new ManaPool(mana)), effectName);
	}

	public static EventFactory addManaToYourManaPoolFromAbility(SetGenerator mana, String effectName)
	{
		EventFactory addMana = new EventFactory(EventType.ADD_MANA, effectName);
		addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
		addMana.parameters.put(EventType.Parameter.MANA, mana);
		addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
		return addMana;
	}

	public static EventFactory addManaToYourManaPoolFromSpell(String mana)
	{
		return addManaToYourManaPoolFromSpell(mana, "Add " + mana + " to your mana pool.");
	}

	public static EventFactory addManaToYourManaPoolFromSpell(String mana, String effectName)
	{
		return addManaToYourManaPoolFromSpell(Identity.fromCollection(new ManaPool(mana)), effectName);
	}

	public static EventFactory addManaToYourManaPoolFromSpell(SetGenerator mana, String effectName)
	{
		EventFactory addMana = new EventFactory(EventType.ADD_MANA, effectName);
		addMana.parameters.put(EventType.Parameter.SOURCE, This.instance());
		addMana.parameters.put(EventType.Parameter.MANA, mana);
		addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
		return addMana;
	}

	public static ContinuousEffect.Part addType(SetGenerator who, Enum<?>... type)
	{
		return addType(who, Identity.instance((Object[])type));
	}

	public static ContinuousEffect.Part addType(SetGenerator who, SetGenerator type)
	{
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, who);
		part.parameters.put(ContinuousEffectType.Parameter.TYPE, type);
		return part;
	}

	public static ZoneChangePattern allyTrigger()
	{
		if(allyTriggerPattern == null)
			allyTriggerPattern = new ImmutableZoneChangePattern(new SimpleZoneChangePattern(null, Battlefield.instance(), Union.instance(ABILITY_SOURCE_OF_THIS, HasSubType.instance(SubType.ALLY)), You.instance(), false));
		return allyTriggerPattern;
	}

	public static ZoneChangePattern asPermanentsEnterTheBattlefield(SetGenerator permanents)
	{
		return new ImmutableZoneChangePattern(new SimpleZoneChangePattern(null, Battlefield.instance(), permanents, false));
	}

	public static ZoneChangePattern asThisEntersTheBattlefield()
	{
		if(asThisEntersTheBattlefieldPattern == null)
			asThisEntersTheBattlefieldPattern = new ImmutableZoneChangePattern(new SimpleZoneChangePattern(null, Battlefield.instance(), This.instance(), false));
		return asThisEntersTheBattlefieldPattern;
	}

	public static EventPattern asThisIsTurnedFaceUp()
	{
		if(asThisIsTurnedFaceUpPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.TURN_PERMANENT_FACE_UP_FINISH);
			pattern.put(EventType.Parameter.OBJECT, This.instance());
			asThisIsTurnedFaceUpPattern = new ImmutableEventPattern(pattern);
		}
		return asThisIsTurnedFaceUpPattern;
	}

	public static EventFactory attach(SetGenerator object, SetGenerator target, String effectName)
	{
		EventFactory ret = new EventFactory(EventType.ATTACH, effectName);
		ret.parameters.put(EventType.Parameter.OBJECT, object);
		ret.parameters.put(EventType.Parameter.TARGET, target);
		return ret;
	}

	public static EventPattern atTheBeginningOfEachEndStep()
	{
		return atTheBeginningOfTheEndStep();
	}

	public static EventPattern atTheBeginningOfEachPlayersUpkeep()
	{
		if(atTheBeginningOfEachPlayersUpkeepPattern == null)
		{
			// TODO: When we get around to multiple players taking the same
			// turns (ex: Two-headed Giant), this will need to be modified to
			// match each player's Upkeep step rather than just the single
			// Upkeep step.
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(Players.instance()));
			atTheBeginningOfEachPlayersUpkeepPattern = new ImmutableEventPattern(pattern);
		}
		return atTheBeginningOfEachPlayersUpkeepPattern;
	}

	public static EventPattern atTheBeginningOfEachUpkeep()
	{
		if(atTheBeginningOfEachUpkeepPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(Players.instance()));
			atTheBeginningOfEachUpkeepPattern = new ImmutableEventPattern(pattern);
		}
		return atTheBeginningOfEachUpkeepPattern;
	}

	public static EventPattern atTheBeginningOfEachOpponentsUpkeeps()
	{
		if(atTheBeginningOfOpponentsUpkeepsPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(OpponentsOf.instance(You.instance())));
			atTheBeginningOfOpponentsUpkeepsPattern = new ImmutableEventPattern(pattern);
		}
		return atTheBeginningOfOpponentsUpkeepsPattern;
	}

	public static EventPattern atTheBeginningOfTheEndStep()
	{
		if(atTheBeginningOfTheEndStepPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, EndStepOf.instance(Players.instance()));
			atTheBeginningOfTheEndStepPattern = new ImmutableEventPattern(pattern);
		}
		return atTheBeginningOfTheEndStepPattern;
	}

	public static EventPattern atTheBeginningOfYourDrawStep()
	{
		if(atTheBeginningOfYourDrawStepPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(You.instance()));
			atTheBeginningOfYourDrawStepPattern = new ImmutableEventPattern(pattern);
		}
		return atTheBeginningOfYourDrawStepPattern;
	}

	public static EventPattern atTheBeginningOfYourEndStep()
	{
		if(atTheBeginningOfYourEndStepPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, EndStepOf.instance(You.instance()));
			atTheBeginningOfYourEndStepPattern = new ImmutableEventPattern(pattern);
		}
		return atTheBeginningOfYourEndStepPattern;
	}

	public static EventPattern atTheBeginningOfYourUpkeep()
	{
		if(atTheBeginningOfYourUpkeepPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(You.instance()));
			atTheBeginningOfYourUpkeepPattern = new ImmutableEventPattern(pattern);
		}
		return atTheBeginningOfYourUpkeepPattern;
	}

	public static EventPattern atEndOfCombat()
	{
		if(atEndOfCombatPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, EndOfCombatStepOf.instance(Players.instance()));
			atEndOfCombatPattern = new ImmutableEventPattern(pattern);
		}
		return atEndOfCombatPattern;
	}

	public static EventPattern battalion()
	{
		if(null == battalionPattern)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ATTACKERS);
			pattern.withResult(new SetPattern()
			{
				@Override
				public boolean match(GameState state, Identified thisObject, Set set)
				{
					if(!thisObject.isTriggeredAbility())
						return false;
					return (set.contains(((TriggeredAbility)thisObject).getSource(state)) && (2 < set.size()));
				}

				@Override
				public void freeze(GameState state, Identified thisObject)
				{
					// Nothing to do here
				}
			});
			battalionPattern = new ImmutableEventPattern(pattern);
		}
		return battalionPattern;
	}

	/**
	 * Create an effect returning objects to their owners' hands.
	 * 
	 * @param objects What to return.
	 * @param effectName The text of the effect.
	 */
	public static EventFactory bounce(SetGenerator objects, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.PUT_INTO_HAND, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PERMANENT, objects);
		return factory;
	}

	/**
	 * Create an effect returning objects to their owners' hands.
	 * 
	 * @param player Who is choosing and who is returning those objects to their
	 * owners' hands
	 * @param number How many objects to choose
	 * @param objects What to return.
	 * @param effectName The text of the effect.
	 */
	public static EventFactory bounceChoice(SetGenerator player, int number, SetGenerator objects, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, player);
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(number));
		factory.parameters.put(EventType.Parameter.CHOICE, objects);
		return factory;
	}

	/**
	 * Creates an event for a floating continuous effect to prevent
	 * regeneration, and then a destroy event for the given objects
	 * 
	 * @param cause The GameObject responsible for creating the fce
	 * @param objects The objects to be destroyed without regeneration
	 * @param effectName The text of the effect.
	 */
	public static EventFactory[] bury(GameObject cause, SetGenerator objects, String effectName)
	{
		// TODO : I hate hate hate this empty effect name, but this is the way
		// the card reads. If someone can figure out a better way to do this...
		// I'm all ears. -RulesGuru
		return new EventFactory[] {stopRegen(cause, objects, ""), destroy(objects, effectName)};
	}

	/**
	 * [things] can't block this turn.
	 */
	public static EventFactory cantBlockThisTurn(SetGenerator what, String effectName)
	{
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
		part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), what)));
		return createFloatingEffect(effectName, part);
	}

	/**
	 * Whenever [this] or another enchantment enters the battlefield under your
	 * control ...
	 */
	public static ZoneChangePattern constellation()
	{
		if(constellationPattern == null)
		{
			SetGenerator otherEnchantments = RelativeComplement.instance(HasType.instance(Type.ENCHANTMENT), ABILITY_SOURCE_OF_THIS);
			SetGenerator stuff = Union.instance(ABILITY_SOURCE_OF_THIS, otherEnchantments);
			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(null, Battlefield.instance(), stuff, You.instance(), false);
			constellationPattern = new ImmutableZoneChangePattern(pattern);
		}
		return constellationPattern;
	}

	/**
	 * Create an effect countering objects.
	 * 
	 * @param what The set of things to counter.
	 * @param effectName The text of the effect.
	 */
	public static EventFactory counter(SetGenerator what, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.COUNTER, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, what);
		return factory;
	}

	/**
	 * Represents "Counter target [x] unless its controller pays [mana]".
	 * 
	 * @param cost The mana cost to pay to prevent countering the spell. Include
	 * parentheses around each symbol. Warning: hybrid symbols suck. don't use
	 * them or die. seriously. its bad news. like, someone might stab you in the
	 * face or something. SERIOUS BUSINESS.
	 * @return An event factory that represents countering the target of "this"
	 * unless its controller pays a mana cost.
	 */
	public static EventFactory counterTargetUnlessControllerPays(String cost, Target target)
	{
		EventFactory counter = counter(targetedBy(target), "Counter target " + target.name);

		SetGenerator controller = ControllerOf.instance(targetedBy(target));
		EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay " + cost);
		pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool(cost)));
		pay.parameters.put(EventType.Parameter.PLAYER, controller);

		return unless(controller, counter, pay, "Counter " + target.name + " unless its controller pays " + cost + ".");
	}

	public static EventFactory createEldraziSpawnTokens(SetGenerator number, String effectName)
	{
		CreateTokensFactory tokens = new CreateTokensFactory(number, numberGenerator(0), numberGenerator(1), effectName);
		tokens.addAbility(org.rnd.jmagic.abilities.SacrificeThisAddOneToYourManaPool.class);
		tokens.setSubTypes(SubType.ELDRAZI, SubType.SPAWN);
		return tokens.getEventFactory();
	}

	public static EventFactory createFloatingEffect(SetGenerator expires, String effectName, ContinuousEffect.Part... effect)
	{
		EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance((Object[])effect));
		factory.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(expires));
		return factory;
	}

	/** Creates a floating continuous effect that lasts until end of turn. */
	public static EventFactory createFloatingEffect(String effectName, ContinuousEffect.Part... effect)
	{
		EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance((Object[])effect));
		return factory;
	}

	/**
	 * Creates a floating continuous effect lasting until end of turn that
	 * generates a replacement effect.
	 */
	public static EventFactory createFloatingReplacement(ReplacementEffect effect, String effectName)
	{
		return createFloatingEffect(effectName, replacementEffectPart(effect));
	}

	public static final class CreateTokensFactory
	{
		private String effectName;
		private EventType effectType;
		private String tokenName;
		private SetGenerator number;
		private SetGenerator power;
		private SetGenerator toughness;
		private Color[] colors;
		private SetGenerator newController;
		private java.util.List<Class<?>> abilities;
		private java.util.Set<SuperType> superTypes;
		private java.util.Set<Type> types;
		private SubType[] subTypes;
		private EventType moveType;

		private java.util.Map<EventType.Parameter, SetGenerator> additionalParameters;

		private CreateTokensFactory()
		{
			this.effectName = "";
			this.effectType = EventType.CREATE_TOKEN_ON_BATTLEFIELD;
			this.tokenName = null;
			this.colors = new Color[0];
			this.number = numberGenerator(1);
			this.newController = You.instance();
			this.abilities = new java.util.LinkedList<Class<?>>();
			this.superTypes = java.util.EnumSet.noneOf(SuperType.class);
			this.types = java.util.EnumSet.noneOf(Type.class);
			this.subTypes = new SubType[0];
			this.moveType = null;

			this.additionalParameters = new java.util.HashMap<EventType.Parameter, SetGenerator>();
		}

		/**
		 * Create an EventFactory creating tokens and putting them onto the
		 * battlefield under the control of {@link You}.
		 * 
		 * @param number How many tokens to create
		 * @param effectName The name of the event creating the tokens
		 */
		public CreateTokensFactory(SetGenerator number, String effectName)
		{
			this();
			this.effectName = effectName;
			this.number = number;
		}

		/**
		 * @return The event factory that will create the tokens. This method
		 * constructs a new EventFactory; if called multiple times, the
		 * references returned will be to different EventFactorys. The result of
		 * the events these factories create will be the tokens themselves, NOT
		 * the {@link ZoneChange}s that put them onto the battlefield.
		 */
		public EventFactory getEventFactory()
		{
			EventFactory factory = new EventFactory(this.effectType, this.effectName);
			if(this.moveType != null)
				factory.parameters.put(EventType.Parameter.EVENT, Identity.instance(this.moveType));

			factory.parameters.putAll(this.additionalParameters);

			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.SUPERTYPE, Identity.fromCollection(this.superTypes));
			if(this.types.contains(Type.CREATURE))
			{
				factory.parameters.put(EventType.Parameter.POWER, this.power);
				factory.parameters.put(EventType.Parameter.TOUGHNESS, this.toughness);
			}
			factory.parameters.put(EventType.Parameter.NUMBER, this.number);
			factory.parameters.put(EventType.Parameter.COLOR, Identity.instance((Object[])this.colors));
			if(this.tokenName != null)
				factory.parameters.put(EventType.Parameter.NAME, Identity.instance(this.tokenName));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.fromCollection(this.types));
			factory.parameters.put(EventType.Parameter.ABILITY, Identity.fromCollection(this.abilities));
			factory.parameters.put(EventType.Parameter.CONTROLLER, this.newController);
			factory.parameters.put(EventType.Parameter.SUBTYPE, Identity.instance((Object)(java.util.Arrays.asList(this.subTypes))));
			return factory;
		}

		/**
		 * Create an EventFactory creating creature tokens and putting them onto
		 * the battlefield under the control of {@link You}.
		 * 
		 * @param number How many tokens to create
		 * @param power The power of each token to create
		 * @param toughness The toughness of each token to create
		 * @param effectName The name of the event creating the tokens
		 */
		public CreateTokensFactory(int number, int power, int toughness, String effectName)
		{
			this(numberGenerator(number), effectName);
			this.addCreature(numberGenerator(power), numberGenerator(toughness));
		}

		/**
		 * Create an EventFactory creating creature tokens and putting them onto
		 * the battlefield under the control of {@link You}.
		 * 
		 * @param number How many tokens to create
		 * @param power The power of each token to create
		 * @param toughness The toughness of each token to create
		 * @param effectName The name of the event creating the tokens
		 */
		public CreateTokensFactory(SetGenerator number, SetGenerator power, SetGenerator toughness, String effectName)
		{
			this(number, effectName);
			this.addCreature(power, toughness);
		}

		public void addAbility(Class<?> ability)
		{
			this.abilities.add(ability);
		}

		public void addCreature(int power, int toughness)
		{
			this.addCreature(numberGenerator(power), numberGenerator(toughness));
		}

		public void addCreature(SetGenerator power, SetGenerator toughness)
		{
			this.power = power;
			this.toughness = toughness;
			this.types.add(Type.CREATURE);
		}

		public void setArtifact()
		{
			this.types.add(Type.ARTIFACT);
		}

		/**
		 * @param defenders Who the token should be attacking, or null if the
		 * controller gets to choose. If the token should come into play tapped,
		 * you want setTappedAndAttacking!
		 */
		public void setAttacking(SetGenerator defenders)
		{
			this.moveType = EventType.PUT_ONTO_BATTLEFIELD_ATTACKING;
			if(defenders != null)
				this.additionalParameters.put(EventType.Parameter.ATTACKER, defenders);
		}

		public void setBlocking(SetGenerator attackers)
		{
			this.effectType = EventType.CREATE_TOKEN_BLOCKING;
			this.additionalParameters.put(EventType.Parameter.ATTACKER, attackers);
		}

		public void setColors(Color... colors)
		{
			this.colors = colors;
		}

		public void setController(SetGenerator newController)
		{
			this.newController = newController;
		}

		public void setEnchantment()
		{
			this.types.add(Type.ENCHANTMENT);
		}

		public void setLegendary()
		{
			this.superTypes.add(SuperType.LEGENDARY);
		}

		public void setName(String tokenName)
		{
			this.tokenName = tokenName;
		}

		public void setSubTypes(SubType... subTypes)
		{
			this.subTypes = subTypes;
		}

		public void setTapped()
		{
			this.moveType = EventType.PUT_ONTO_BATTLEFIELD_TAPPED;
		}

		/**
		 * @param defenders Who the token should be attacking, or null if the
		 * controller gets to choose
		 */
		public void setTappedAndAttacking(SetGenerator defenders)
		{
			this.moveType = EventType.PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING;
			if(defenders != null)
				this.additionalParameters.put(EventType.Parameter.ATTACKER, defenders);
		}
	}

	public static SetGenerator delayedTriggerContext(SetGenerator toReevaluate)
	{
		return Impersonate.instance(DelayedTriggerCausingObject.instance(This.instance()), toReevaluate);
	}

	/**
	 * Create an effect destroying objects.
	 * 
	 * @param what What to destroy
	 * @param effectName Text of the effect
	 */
	public static EventFactory destroy(SetGenerator what, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.DESTROY_PERMANENTS, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PERMANENT, what);
		return factory;
	}

	/**
	 * Create an effect detaining objects.
	 * 
	 * NOTE: Make sure to call GameState.ensureTracker(new
	 * DetainGenerator.Tracker()) !!!
	 * 
	 * @param what What to detain
	 * @param effectName Text of the effect
	 */
	public static EventFactory detain(SetGenerator what, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.DETAIN, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PERMANENT, what);
		return factory;
	}

	/**
	 * Create an effect discarding cards from players' hands.
	 * 
	 * @param who Who will discard the cards
	 * @param number How many cards to discard
	 * @param effectName Text of the effect
	 */
	public static EventFactory discardCards(SetGenerator who, int number, String effectName)
	{
		return discardCards(who, numberGenerator(number), effectName);
	}

	/**
	 * Create an effect discarding cards from players' hands.
	 * 
	 * @param who Who will discard the cards
	 * @param number How many cards to discard
	 * @param effectName Text of the effect
	 */
	public static EventFactory discardCards(SetGenerator who, SetGenerator number, String effectName)
	{
		return discardCards(who, number, null, effectName);
	}

	/**
	 * Create an effect discarding cards from players' hands.
	 * 
	 * @param who Who will discard the cards
	 * @param number How many cards to discard
	 * @param choices The cards to choose from -- a filter, this method will
	 * intersect with the hand for you
	 * @param effectName Text of the effect
	 */
	public static EventFactory discardCards(SetGenerator who, int number, SetGenerator choices, String effectName)
	{
		return discardCards(who, numberGenerator(number), choices, effectName);
	}

	/**
	 * Create an effect discarding cards from players' hands.
	 * 
	 * @param who Who will discard the cards
	 * @param number How many cards to discard
	 * @param choices The cards to choose from -- a filter, this method will
	 * intersect with the hand for you
	 * @param effectName Text of the effect
	 */
	public static EventFactory discardCards(SetGenerator who, SetGenerator number, SetGenerator choices, String effectName)
	{
		EventFactory discard = new EventFactory(EventType.DISCARD_CHOICE, effectName);
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.NUMBER, number);
		discard.parameters.put(EventType.Parameter.PLAYER, who);
		if(choices != null)
			discard.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(choices, InZone.instance(HandOf.instance(who))));
		return discard;
	}

	/**
	 * Create an effect discarding all cards from players' hands.
	 * 
	 * @param who Who will discard the cards
	 * @param effectName Text of the effect
	 */
	public static EventFactory discardHand(SetGenerator who, String effectName)
	{
		EventFactory discard = new EventFactory(EventType.DISCARD_CARDS, effectName);
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.CARD, InZone.instance(HandOf.instance(who)));
		discard.parameters.put(EventType.Parameter.PLAYER, who);
		return discard;
	}

	public static EventFactory drawACard()
	{
		return drawCards(You.instance(), 1, "Draw a card.");
	}

	/**
	 * Create an effect drawing cards into players' hands.
	 * 
	 * @param who Who will draw the cards
	 * @param number How many cards to draw
	 * @param effectName Text of the effect
	 */
	public static EventFactory drawCards(SetGenerator who, int number, String effectName)
	{
		return drawCards(who, numberGenerator(number), effectName);
	}

	/**
	 * Create an effect drawing cards into players' hands using
	 * {@link EventType#DRAW_CARDS}.
	 * 
	 * @param who Who will draw the cards
	 * @param number How many cards to draw
	 * @param effectName Text of the effect
	 */
	public static EventFactory drawCards(SetGenerator who, SetGenerator number, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.DRAW_CARDS, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, who);
		factory.parameters.put(EventType.Parameter.NUMBER, number);
		return factory;
	}

	public static SetGenerator effectResultFrom(EventFactory effect, SetGenerator sourceObject)
	{
		return Impersonate.instance(sourceObject, EffectResult.instance(effect));
	}

	/**
	 * Create an effect choosing and exiling objects.
	 * 
	 * @param who Who is doing the exiling.
	 * @param what What to choose amongst to exile.
	 * @param number How many objects to exile.
	 * @param effectName Text of the effect.
	 */
	public static EventFactory exile(SetGenerator who, SetGenerator what, int number, String effectName)
	{
		return exile(who, what, numberGenerator(number), effectName);
	}

	/**
	 * Create an effect choosing and exiling objects.
	 * 
	 * @param who Who is doing the exiling.
	 * @param what What to choose amongst to exile.
	 * @param number How many objects to exile.
	 * @param effectName Text of the effect.
	 */
	public static EventFactory exile(SetGenerator who, SetGenerator what, SetGenerator number, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.MOVE_CHOICE, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, number);
		factory.parameters.put(EventType.Parameter.OBJECT, what);
		factory.parameters.put(EventType.Parameter.CHOICE, Identity.instance(PlayerInterface.ChooseReason.EXILE));
		factory.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, who);
		return factory;
	}

	/**
	 * Create a {@link EventType#MOVE_OBJECTS} effect exiling objects from a
	 * single zone. The event's result will be the zone changes.
	 * 
	 * @param what What to exile.
	 * @param effectName Text of the effect.
	 */
	public static EventFactory exile(SetGenerator what, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, what);
		return factory;
	}

	public static EventFactory exileThis(String thisName)
	{
		return exile(ABILITY_SOURCE_OF_THIS, "Exile " + thisName);
	}

	/**
	 * This takes care of ensuring the LeavesTheBattlefieldTracker.
	 * 
	 * @param state State to ensure the tracker in
	 * @param object What to exile
	 * @param effectName Text of the effect
	 * @return Exile [object] until ~ leaves the battlefield.
	 */
	public static EventFactory exileUntilThisLeavesTheBattlefield(GameState state, SetGenerator object, String effectName)
	{
		state.ensureTracker(new LeftTheBattlefield.LeavesTheBattlefieldTracker());
		SetGenerator thisIsGone = Intersect.instance(ABILITY_SOURCE_OF_THIS, LeftTheBattlefield.instance());

		EventFactory exileUntil = new EventFactory(EventType.EXILE_UNTIL, effectName);
		exileUntil.parameters.put(EventType.Parameter.CAUSE, This.instance());
		exileUntil.parameters.put(EventType.Parameter.OBJECT, object);
		exileUntil.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(thisIsGone));
		return exileUntil;
	}

	/**
	 * IMPORTANT NOTE: The who generator must resolve to two objects that can
	 * either be 2 Targets, 1 Target and 1 GameObject, or 2 GameObjects. If
	 * there is a possibility that both objects could represent the same
	 * creature, they are not allowed to both be represented by GameObjects.
	 * Otherwise, Set uniqueness will remove one, and performing the event will
	 * fail.
	 */
	public static EventFactory fight(SetGenerator who, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.FIGHT, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, ABILITY_SOURCE_OF_THIS);
		factory.parameters.put(EventType.Parameter.OBJECT, who);
		return factory;
	}

	/**
	 * Create an effect gaining life for players.
	 */
	public static EventFactory gainLife(SetGenerator who, int amount, String effectName)
	{
		return gainLife(who, numberGenerator(amount), effectName);
	}

	/**
	 * Create an effect gaining life for players.
	 */
	public static EventFactory gainLife(SetGenerator who, SetGenerator amount, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.GAIN_LIFE, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, who);
		factory.parameters.put(EventType.Parameter.NUMBER, amount);
		return factory;
	}

	public static String getName(Class<?> cls)
	{
		Name name = cls.getAnnotation(Name.class);
		if(name == null)
			return null;
		return name.value();
	}

	/**
	 * If this ability has been activated N or more times this turn, sacrifice
	 * this permanent.
	 * 
	 * @param state The game state in which the object receiving this effect
	 * exists.
	 * @param n "N" in the above description.
	 * @param cardName The name of the object receiving this effect.
	 */
	public static EventFactory ifActivatedNOrMoreTimesSacrifice(GameState state, int n, String cardName)
	{
		// If this ability has been activated four or more times this turn,
		state.ensureTracker(new ActivatedAbility.ActivationsThisTurn());
		SetGenerator numActivations = TimesActivatedThisTurn.instance(This.instance());
		SetGenerator fourOrMore = Between.instance(n, null);
		SetGenerator condition = Intersect.instance(numActivations, fourOrMore);

		// sacrifice ~
		SetGenerator delayedTrigger = This.instance();
		SetGenerator dragonWhelp = AbilitySource.instance(delayedTrigger);

		EventFactory sacFactory = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice " + cardName + ".");
		sacFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		sacFactory.parameters.put(EventType.Parameter.PERMANENT, dragonWhelp);

		// at the beginning of the next end step.
		EventType.ParameterMap triggerParameters = new EventType.ParameterMap();
		triggerParameters.put(EventType.Parameter.CAUSE, This.instance());
		triggerParameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
		triggerParameters.put(EventType.Parameter.EFFECT, Identity.instance(sacFactory));

		EventFactory factory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If this ability was activated four or more times this turn, sacrifice " + cardName + " at the beginning of the next end step.");
		factory.parameters.put(EventType.Parameter.IF, condition);
		factory.parameters.put(EventType.Parameter.THEN, Identity.instance(new EventFactory(EventType.CREATE_DELAYED_TRIGGER, triggerParameters, "Sacrifice Dragon Whelp at the beginning of the next end step.")));
		return factory;
	}

	public static EventFactory ifElse(SetGenerator ifCondition, EventFactory elseEvent, String name)
	{
		EventFactory ret = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, name);
		ret.parameters.put(EventType.Parameter.IF, ifCondition);
		ret.parameters.put(EventType.Parameter.ELSE, Identity.instance(elseEvent));
		return ret;
	}

	public static EventFactory ifElse(EventFactory ifEvent, EventFactory elseEvent, String name)
	{
		EventFactory ret = new EventFactory(EventType.IF_EVENT_THEN_ELSE, name);
		ret.parameters.put(EventType.Parameter.IF, Identity.instance(ifEvent));
		ret.parameters.put(EventType.Parameter.ELSE, Identity.instance(elseEvent));
		return ret;
	}

	public static EventFactory ifThen(SetGenerator ifCondition, EventFactory thenEvent, String name)
	{
		EventFactory ret = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, name);
		ret.parameters.put(EventType.Parameter.IF, ifCondition);
		ret.parameters.put(EventType.Parameter.THEN, Identity.instance(thenEvent));
		return ret;
	}

	public static EventFactory ifThenElse(SetGenerator ifCondition, EventFactory thenEvent, EventFactory elseEvent, String name)
	{
		EventFactory ret = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, name);
		ret.parameters.put(EventType.Parameter.IF, ifCondition);
		ret.parameters.put(EventType.Parameter.THEN, Identity.instance(thenEvent));
		ret.parameters.put(EventType.Parameter.ELSE, Identity.instance(elseEvent));
		return ret;
	}

	public static EventFactory ifThen(EventFactory ifEvent, EventFactory thenEvent, String name)
	{
		EventFactory ret = new EventFactory(EventType.IF_EVENT_THEN_ELSE, name);
		ret.parameters.put(EventType.Parameter.IF, Identity.instance(ifEvent));
		ret.parameters.put(EventType.Parameter.THEN, Identity.instance(thenEvent));
		return ret;
	}

	public static EventFactory ifThenElse(EventFactory ifEvent, EventFactory thenEvent, EventFactory elseEvent, String name)
	{
		EventFactory ret = new EventFactory(EventType.IF_EVENT_THEN_ELSE, name);
		ret.parameters.put(EventType.Parameter.IF, Identity.instance(ifEvent));
		if(thenEvent != null)
			ret.parameters.put(EventType.Parameter.THEN, Identity.instance(thenEvent));
		if(elseEvent != null)
			ret.parameters.put(EventType.Parameter.ELSE, Identity.instance(elseEvent));
		return ret;
	}

	public static EventPattern heroic()
	{
		if(heroicPattern == null)
		{
			SimpleEventPattern target = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			target.put(EventType.Parameter.PLAYER, You.instance());
			target.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasTarget.instance(ABILITY_SOURCE_OF_THIS)));
			heroicPattern = new ImmutableEventPattern(target);
		}
		return heroicPattern;
	}

	public static EventPattern inspired()
	{
		if(inspiredPattern == null)
		{
			SimpleEventPattern untap = new SimpleEventPattern(EventType.UNTAP_ONE_PERMANENT);
			untap.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			inspiredPattern = new ImmutableEventPattern(untap);
		}
		return inspiredPattern;
	}

	public static ZoneChangePattern landfall()
	{
		if(landfallPattern == null)
			landfallPattern = new ImmutableZoneChangePattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasType.instance(Type.LAND), You.instance(), false));
		return landfallPattern;
	}

	/**
	 * This is similar to using
	 * <code>SimpleSetPattern(LandPermanents.instance())</code>, except that
	 * this <code>SetPattern</code> will match against ghosts.
	 */
	public static SetPattern landPermanents()
	{
		if(landPermanents == null)
			landPermanents = new SetPattern()
			{
				@Override
				public void freeze(GameState state, Identified thisObject)
				{
					// Nothing to freeze
				}

				@Override
				public boolean match(GameState state, Identified thisObject, Set set)
				{
					for(GameObject object: set.getAll(GameObject.class))
						if(object.isPermanent() && object.getTypes().contains(Type.LAND))
							return true;
					return false;
				}
			};
		return landPermanents;
	}

	/**
	 * This is similar to using
	 * <code>SimpleSetPattern(Intersect.instance(LandPermanents.instance(), HasSubType.instance(landType)))</code>
	 * , except that this <code>SetPattern</code> will match against ghosts.
	 */
	public static SetPattern landPermanents(final SubType landType)
	{
		return new SetPattern()
		{
			@Override
			public void freeze(GameState state, Identified thisObject)
			{
				// Nothing to freeze
			}

			@Override
			public boolean match(GameState state, Identified thisObject, Set set)
			{
				for(GameObject object: set.getAll(GameObject.class))
					if(object.isPermanent() && object.getTypes().contains(Type.LAND) && object.getSubTypes().contains(landType))
						return true;
				return false;
			}
		};
	}

	/**
	 * @param who Who is looking at the objects
	 * @param what Objects and/or zones to look at
	 * @param name The name of the effect
	 */
	public static EventFactory look(SetGenerator who, SetGenerator what, String name)
	{
		EventFactory f = new EventFactory(EventType.LOOK, name);
		f.parameters.put(EventType.Parameter.CAUSE, This.instance());
		f.parameters.put(EventType.Parameter.PLAYER, who);
		f.parameters.put(EventType.Parameter.OBJECT, what);
		return f;
	}

	/**
	 * Create an effect losing life for players.
	 */
	public static EventFactory loseLife(SetGenerator who, int amount, String effectName)
	{
		return loseLife(who, numberGenerator(amount), effectName);
	}

	/**
	 * Create an effect losing life for players.
	 */
	public static EventFactory loseLife(SetGenerator who, SetGenerator amount, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.LOSE_LIFE, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, who);
		factory.parameters.put(EventType.Parameter.NUMBER, amount);
		return factory;
	}

	/**
	 * Create an effect putting the top cards of players' libraries into
	 * players' graveyards.
	 * 
	 * @param who Who will mill the cards
	 * @param number How many cards to mill
	 * @param effectName text of the effect
	 */
	public static EventFactory millCards(SetGenerator who, int number, String effectName)
	{
		return millCards(who, numberGenerator(number), effectName);
	}

	/**
	 * Create an effect putting the top cards of players' libraries into
	 * players' graveyards.
	 * 
	 * @param who Who will mill the cards
	 * @param number How many cards to mill
	 * @param effectName text of the effect
	 */
	public static EventFactory millCards(SetGenerator who, SetGenerator number, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.MILL_CARDS, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, who);
		factory.parameters.put(EventType.Parameter.NUMBER, number);
		return factory;
	}

	public static ContinuousEffect.Part modifyPowerAndToughness(SetGenerator who, int power, int toughness)
	{
		return modifyPowerAndToughness(who, numberGenerator(power), numberGenerator(toughness));
	}

	public static ContinuousEffect.Part modifyPowerAndToughness(SetGenerator who, SetGenerator power, SetGenerator toughness)
	{
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MODIFY_POWER_AND_TOUGHNESS);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, who);
		part.parameters.put(ContinuousEffectType.Parameter.POWER, power);
		part.parameters.put(ContinuousEffectType.Parameter.TOUGHNESS, toughness);
		return part;
	}

	/**
	 * Requires
	 * {@link org.rnd.jmagic.engine.eventTypes.Monstrosity.MonstrousTracker} !!
	 * Don't forget!
	 */
	public static EventFactory monstrosity(int N)
	{
		EventFactory ret = new EventFactory(EventType.MONSTROSITY, "Monstrosity " + N + ".");
		ret.parameters.put(EventType.Parameter.CAUSE, This.instance());
		ret.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
		ret.parameters.put(EventType.Parameter.NUMBER, numberGenerator(N));
		return ret;
	}

	/**
	 * Provides a SetGenerator that evaluates to the given number. This will
	 * usually be an Identity, but if a "specific" generator is available (Zero,
	 * for instance) it will be returned instead.
	 */
	public static SetGenerator numberGenerator(int N)
	{
		if(N == -1)
			return NegativeOne.instance();
		if(N == 0)
			return Zero.instance();
		if(N == 1)
			return One.instance();

		// TODO : Would it be at all useful to cache these Identities, or would
		// getting them out of a map be more time-consuming than just generating
		// them?
		// Kamikaze - I'm for this change. Even if its more time consuming,
		// it'll save
		// memory.
		return Identity.instance(N);
	}

	public static EventFactory payLife(SetGenerator who, int amount, String effectName)
	{
		return payLife(who, numberGenerator(amount), effectName);
	}

	public static EventFactory payLife(SetGenerator who, SetGenerator amount, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.PAY_LIFE, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, who);
		factory.parameters.put(EventType.Parameter.NUMBER, amount);
		return factory;
	}

	/**
	 * This is similar to using
	 * <code>SimpleSetPattern(Permanents.instance())</code>, except that this
	 * <code>SetPattern</code> will match against ghosts.
	 */
	public static SetPattern permanents()
	{
		if(permanents == null)
			permanents = new SetPattern()
			{
				@Override
				public boolean match(GameState state, Identified thisObject, Set set)
				{
					for(GameObject object: set.getAll(GameObject.class))
						if(object.isPermanent())
							return true;
					return false;
				}

				@Override
				public void freeze(GameState state, Identified thisObject)
				{
					// Nothing to freeze
				}
			};
		return permanents;
	}

	/**
	 * Create an effect dealing damage to objects or players from a permanent.
	 * 
	 * @param amount The amount of damage to deal
	 * @param taker Who is taking damage
	 * @param effectName Text that describes who is taking damage
	 */
	public static EventFactory permanentDealDamage(int amount, SetGenerator taker, String effectName)
	{
		return permanentDealDamage(numberGenerator(amount), taker, effectName);
	}

	/**
	 * Create an effect dealing damage to objects or players from a permanent.
	 * 
	 * @param amount The amount of damage to deal
	 * @param taker Who is taking damage
	 * @param effectName Text that describes who is taking damage
	 */
	public static EventFactory permanentDealDamage(SetGenerator amount, SetGenerator taker, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, effectName);
		factory.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
		factory.parameters.put(EventType.Parameter.NUMBER, amount);
		factory.parameters.put(EventType.Parameter.TAKER, taker);
		return factory;
	}

	/**
	 * See
	 * {@link #playerChoose(SetGenerator, SetGenerator, SetGenerator, PlayerInterface.ChoiceType, PlayerInterface.ChooseReason, String)}
	 * for important information.
	 */
	public static EventFactory playerChoose(SetGenerator choosing, int number, SetGenerator choices, PlayerInterface.ChoiceType choiceType, PlayerInterface.ChooseReason reason, String name)
	{
		return playerChoose(choosing, numberGenerator(number), choices, choiceType, reason, name);
	}

	/**
	 * Note that the {@link EventType#PLAYER_CHOOSE} {@link EventFactory}
	 * returned always specifies {@link This#instance()} for
	 * {@link org.rnd.jmagic.engine.EventType.Parameter#OBJECT}.
	 */
	public static EventFactory playerChoose(SetGenerator choosing, SetGenerator number, SetGenerator choices, PlayerInterface.ChoiceType choiceType, PlayerInterface.ChooseReason reason, String name)
	{
		EventFactory f = new EventFactory(EventType.PLAYER_CHOOSE, name);
		f.parameters.put(EventType.Parameter.PLAYER, choosing);
		f.parameters.put(EventType.Parameter.NUMBER, number);
		f.parameters.put(EventType.Parameter.CHOICE, choices);
		f.parameters.put(EventType.Parameter.TYPE, Identity.instance(choiceType, reason));
		f.parameters.put(EventType.Parameter.OBJECT, This.instance());
		return f;
	}

	/**
	 * @param who The player choosing whether to do what
	 * @param what Any event, except for a PAY_MANA involving (X).
	 * @param effectName the text of the effect
	 * 
	 * @return An {@link EventFactory} of type {@link EventType#PLAYER_MAY}
	 */
	public static EventFactory playerMay(SetGenerator who, EventFactory what, String effectName)
	{
		if(what.type == EventType.PAY_MANA)
		{
			EventFactory factory = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, effectName);
			factory.parameters.put(EventType.Parameter.CAUSE, what.parameters.get(EventType.Parameter.CAUSE));
			factory.parameters.put(EventType.Parameter.COST, what.parameters.get(EventType.Parameter.COST));
			factory.parameters.put(EventType.Parameter.PLAYER, who);
			if(what.parameters.containsKey(EventType.Parameter.NUMBER))
				factory.parameters.put(EventType.Parameter.NUMBER, what.parameters.get(EventType.Parameter.NUMBER));
			return factory;
		}

		EventFactory factory = new EventFactory(EventType.PLAYER_MAY, effectName);
		factory.parameters.put(EventType.Parameter.PLAYER, who);
		factory.parameters.put(EventType.Parameter.EVENT, Identity.instance(what));
		return factory;
	}

	/**
	 * [Player] may play [n] additional land(s) this turn.
	 * 
	 * @param who SetGenerator describing who can play the lands.
	 * @param number How many additional lands can be played.
	 * @param effectName Text of the effect.
	 * @return An <code>EventFactory</code> that will create a floating
	 * continuous effect giving the specified player extra play land actions.
	 */
	public static EventFactory playExtraLands(SetGenerator who, final int number, String effectName)
	{
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PLAY_ADDITIONAL_LANDS);

		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, who);
		part.parameters.put(ContinuousEffectType.Parameter.NUMBER, numberGenerator(number));

		return createFloatingEffect(effectName, part);
	}

	public static EventFactory populate()
	{
		return populate("Populate.");
	}

	public static EventFactory populate(String effectName)
	{
		EventFactory factory = new EventFactory(EventType.POPULATE, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		return factory;
	}

	public static EventFactory proliferate()
	{
		return proliferate(You.instance(), "Proliferate.");
	}

	public static EventFactory proliferate(SetGenerator who, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.PROLIFERATE, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, who);
		return factory;
	}

	public static EventFactory ptChangeAndAbilityUntilEndOfTurn(SetGenerator who, int power, int toughness, String effectName, Class<?>... abilities)
	{
		return createFloatingEffect(effectName, modifyPowerAndToughness(who, power, toughness), addAbilityToObject(who, abilities));
	}

	public static EventFactory ptChangeAndAbilityUntilEndOfTurn(SetGenerator who, SetGenerator power, SetGenerator toughness, String effectName, Class<?>... abilities)
	{
		return createFloatingEffect(effectName, modifyPowerAndToughness(who, power, toughness), addAbilityToObject(who, abilities));
	}

	/**
	 * Create an effect setting the power and toughness of objects until end of
	 * turn.
	 * 
	 * @param what SetGenerator that describes what's getting the p/t change
	 * @param power Change to power
	 * @param toughness Change to toughness
	 * @param effectName Text of the effect
	 */
	public static EventFactory ptChangeUntilEndOfTurn(SetGenerator what, int power, int toughness, String effectName)
	{
		return createFloatingEffect(effectName, modifyPowerAndToughness(what, power, toughness));
	}

	/**
	 * Create an effect setting the power and toughness of objects until end of
	 * turn.
	 * 
	 * @param what SetGenerator that describes what's getting the p/t change
	 * @param power Change to power
	 * @param toughness Change to toughness
	 * @param effectName Text of the effect
	 */
	public static EventFactory ptChangeUntilEndOfTurn(SetGenerator what, SetGenerator power, SetGenerator toughness, String effectName)
	{
		return createFloatingEffect(effectName, modifyPowerAndToughness(what, power, toughness));
	}

	/**
	 * (2, -2) for example becomes "+2/-2".
	 */
	public static String ptChangeText(int power, int toughness)
	{
		if(power < 0)
		{
			if(toughness < 0)
				return "" + power + "/" + toughness;
			if(toughness == 0)
				return "" + power + "/-0";
			// toughness > 0
			return "" + power + "/+" + toughness;
		}
		else if(power == 0)
		{
			if(toughness < 0)
				return "-0/" + toughness;
			// toughness >= 0
			return "+0/+" + toughness;
		}
		else
		// power > 0
		{
			if(toughness < 0)
				return "+" + power + "/" + toughness;
			// toughness >= 0
			return "+" + power + "/+" + toughness;
		}
	}

	/**
	 * Create an effect putting counters on objects.
	 * 
	 * @param number How many counters to place.
	 * @param type What kind of counters to place.
	 * @param onWhat What to place the counters on.
	 * @param effectName Text of the effect.
	 */
	public static EventFactory putCounters(int number, Counter.CounterType type, SetGenerator onWhat, String effectName)
	{
		return putCounters(numberGenerator(number), type, onWhat, effectName);
	}

	/**
	 * Create an effect putting counters on objects.
	 * 
	 * @param number How many counters to place.
	 * @param type What kind of counters to place.
	 * @param onWhat What to place the counters on.
	 * @param effectName Text of the effect.
	 */
	public static EventFactory putCounters(SetGenerator number, Counter.CounterType type, SetGenerator onWhat, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, number);
		factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(type));
		factory.parameters.put(EventType.Parameter.OBJECT, onWhat);
		return factory;
	}

	/**
	 * Create an effect putting counters on the source of this effect (uses
	 * AbilitySource(This)).
	 * 
	 * @param N How many counters to place.
	 * @param type What kind of counters to place.
	 * @param cardName Name of the card.
	 * @return The effect.
	 */
	public static EventFactory putCountersOnThis(int N, Counter.CounterType type, String cardName)
	{
		String name = "Put " + org.rnd.util.NumberNames.getAOrAn(N, type.toString()) + " " + type + (N > 1 ? "s" : "") + " on " + cardName;
		return putCounters(N, type, ABILITY_SOURCE_OF_THIS, name);
	}

	public static EventFactory putIntoGraveyard(SetGenerator what, String name)
	{
		EventFactory f = new EventFactory(EventType.PUT_INTO_GRAVEYARD, name);
		f.parameters.put(EventType.Parameter.CAUSE, This.instance());
		f.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		f.parameters.put(EventType.Parameter.OBJECT, what);
		return f;
	}

	public static EventFactory putIntoHand(SetGenerator what, SetGenerator whoseHand, String name)
	{
		EventFactory f = new EventFactory(EventType.MOVE_OBJECTS, name);
		f.parameters.put(EventType.Parameter.CAUSE, This.instance());
		f.parameters.put(EventType.Parameter.TO, HandOf.instance(whoseHand));
		f.parameters.put(EventType.Parameter.OBJECT, what);
		return f;
	}

	public static EventFactory putOntoBattlefield(SetGenerator what, String name)
	{
		return putOntoBattlefield(what, You.instance(), name, false);
	}

	public static EventFactory putOntoBattlefield(SetGenerator what, String name, boolean tapped)
	{
		return putOntoBattlefield(what, You.instance(), name, tapped);
	}

	public static EventFactory putOntoBattlefield(SetGenerator what, SetGenerator controller, String name, boolean tapped)
	{
		EventFactory f = new EventFactory(tapped ? EventType.PUT_ONTO_BATTLEFIELD_TAPPED : EventType.PUT_ONTO_BATTLEFIELD, name);
		f.parameters.put(EventType.Parameter.CAUSE, This.instance());
		f.parameters.put(EventType.Parameter.CONTROLLER, controller);
		f.parameters.put(EventType.Parameter.OBJECT, what);
		return f;
	}

	public static EventFactory putOnBottomOfLibrary(SetGenerator what, String name)
	{
		EventFactory f = new EventFactory(EventType.PUT_INTO_LIBRARY, name);
		f.parameters.put(EventType.Parameter.CAUSE, This.instance());
		f.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
		f.parameters.put(EventType.Parameter.OBJECT, what);
		return f;
	}

	public static EventFactory putOnTopOfLibrary(SetGenerator what, String name)
	{
		EventFactory f = new EventFactory(EventType.PUT_INTO_LIBRARY, name);
		f.parameters.put(EventType.Parameter.CAUSE, This.instance());
		f.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		f.parameters.put(EventType.Parameter.OBJECT, what);
		return f;
	}

	public static EventFactory random(SetGenerator choices, String effectName)
	{
		return random(choices, 1, effectName);
	}

	public static EventFactory random(SetGenerator choices, int number, String effectName)
	{
		return random(choices, numberGenerator(number), effectName);
	}

	public static EventFactory random(SetGenerator choices, SetGenerator number, String effectName)
	{
		EventFactory factory = new EventFactory(RANDOM, effectName);
		factory.parameters.put(EventType.Parameter.OBJECT, choices);
		factory.parameters.put(EventType.Parameter.NUMBER, number);
		return factory;
	}

	/**
	 * Creates an event factory that regenerates an object; that is, that effect
	 * creates a one-use regeneration shield on that object (using
	 * {@link EventType#CREATE_REGENERATION_SHIELD}).
	 * 
	 * @param what What to regenerate.
	 * @param effectName The name of the effect.
	 * @return The effect.
	 */
	public static EventFactory regenerate(SetGenerator what, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.CREATE_REGENERATION_SHIELD, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, what);
		return factory;
	}

	public static EventFactory removeCounters(int N, Counter.CounterType type, SetGenerator fromWhat, String effectName)
	{
		return removeCounters(numberGenerator(N), type, fromWhat, effectName);
	}

	/**
	 * @param N The number to remove (or null to remove all)
	 * @param type The type of counter to remove
	 * @param fromWhat The GameObject or Player to remove counters from
	 * @param effectName The name of the created event
	 */
	public static EventFactory removeCounters(SetGenerator N, Counter.CounterType type, SetGenerator fromWhat, String effectName)
	{
		EventFactory effect = new EventFactory(EventType.REMOVE_COUNTERS, effectName);
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.COUNTER, Identity.instance(type));
		if(N != null)
			effect.parameters.put(EventType.Parameter.NUMBER, N);
		effect.parameters.put(EventType.Parameter.OBJECT, fromWhat);
		return effect;
	}

	public static EventFactory removeCountersFromThis(int N, Counter.CounterType type, String cardName)
	{
		String name = "Remove " + org.rnd.util.NumberNames.getAOrAn(N, type.toString()) + " " + type + (N > 1 ? "s" : "") + " from " + cardName;
		return removeCounters(N, type, ABILITY_SOURCE_OF_THIS, name);
	}

	public static EventFactory removeCountersFromThis(SetGenerator N, Counter.CounterType type, String effectName)
	{
		EventFactory effect = new EventFactory(EventType.REMOVE_COUNTERS, effectName);
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.COUNTER, Identity.instance(type));
		effect.parameters.put(EventType.Parameter.NUMBER, N);
		effect.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
		return effect;
	}

	public static ContinuousEffect.Part replacementEffectPart(ReplacementEffect effect)
	{
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(effect));
		return part;
	}

	/**
	 * @param what Objects and/or zones to reveal
	 * @param name The name of the effect
	 * @return An {@link EventFactory} of type {@link EventType#REVEAL}.
	 */
	public static EventFactory reveal(SetGenerator what, String name)
	{
		EventFactory f = new EventFactory(EventType.REVEAL, name);
		f.parameters.put(EventType.Parameter.CAUSE, This.instance());
		f.parameters.put(EventType.Parameter.OBJECT, what);
		return f;
	}

	/**
	 * Creates an event factory that causes a player to choose and sacrifice
	 * permanents.
	 */
	public static EventFactory sacrifice(SetGenerator who, int number, SetGenerator filter, String effectName)
	{
		return sacrifice(who, numberGenerator(number), filter, effectName);
	}

	/**
	 * Creates an event factory that causes a player to choose and sacrifice
	 * permanents.
	 */
	public static EventFactory sacrifice(SetGenerator who, SetGenerator number, SetGenerator filter, String effectName)
	{
		EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_CHOICE, effectName);
		sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrifice.parameters.put(EventType.Parameter.NUMBER, number);
		sacrifice.parameters.put(EventType.Parameter.CHOICE, filter);
		sacrifice.parameters.put(EventType.Parameter.PLAYER, who);
		return sacrifice;
	}

	/**
	 * effect name is "sacrifice a creature" -- if you want a different one,
	 * pass it in.
	 */
	public static EventFactory sacrificeACreature()
	{
		return sacrificeACreature("sacrifice a creature");
	}

	public static EventFactory sacrificeACreature(String effectName)
	{
		return sacrifice(You.instance(), 1, CreaturePermanents.instance(), effectName);
	}

	public static EventFactory sacrificeThis(String name)
	{
		return sacrificeSpecificPermanents(You.instance(), ABILITY_SOURCE_OF_THIS, "Sacrifice " + name);
	}

	public static EventFactory sacrificeSpecificPermanents(SetGenerator who, SetGenerator what, String text)
	{
		EventFactory factory = new EventFactory(EventType.SACRIFICE_PERMANENTS, text);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, who);
		factory.parameters.put(EventType.Parameter.PERMANENT, what);
		return factory;
	}

	public static EventFactory scry(int number, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.SCRY, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(number));
		return factory;
	}

	public static EventFactory searchYourLibraryForABasicLandCardAndPutItIntoYourHand()
	{
		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
		return factory;
	}

	public static EventFactory searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(boolean tapped)
	{
		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card and put that card onto the battlefield" + (tapped ? " tapped" : "") + ". Then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		if(tapped)
			factory.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
		factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
		return factory;
	}

	public static EventFactory separateIntoPiles(SetGenerator who, SetGenerator what, int number, String name)
	{
		EventFactory f = new EventFactory(SEPARATE_INTO_PILES, name);
		f.parameters.put(EventType.Parameter.PLAYER, who);
		f.parameters.put(EventType.Parameter.CARD, what);
		f.parameters.put(EventType.Parameter.NUMBER, numberGenerator(number));
		return f;
	}

	public static EventFactory sequence(EventFactory... sequence)
	{
		java.util.List<EventFactory> events = java.util.Arrays.asList(sequence);
		String eventName = org.rnd.util.SeparatedList.get(" ", "", events).toString();

		EventFactory ret = new EventFactory(SEQUENCE, eventName);
		ret.parameters.put(EventType.Parameter.EFFECT, Identity.instance((Object)events));
		return ret;
	}

	/**
	 * Creates a {@link ContinuousEffectType#SET_POWER_AND_TOUGHNESS} effect.
	 * See that effect type's documentation.
	 */
	public static ContinuousEffect.Part setPowerAndToughness(SetGenerator who, int power, int toughness)
	{
		return setPowerAndToughness(who, numberGenerator(power), numberGenerator(toughness));
	}

	/**
	 * Creates a {@link ContinuousEffectType#SET_POWER_AND_TOUGHNESS} effect.
	 * See that effect type's documentation.
	 */
	public static ContinuousEffect.Part setPowerAndToughness(SetGenerator who, SetGenerator power, SetGenerator toughness)
	{
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_POWER_AND_TOUGHNESS);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, who);
		if(power != null)
			part.parameters.put(ContinuousEffectType.Parameter.POWER, power);
		if(toughness != null)
			part.parameters.put(ContinuousEffectType.Parameter.TOUGHNESS, toughness);
		return part;
	}

	/**
	 * Creates an {@link EventFactory} that shuffles a player's library.
	 * 
	 * @param whoseLibrary Whose library to shuffle.
	 * @param effectName The text of the effect.
	 * @return The event factory shuffling the player's library.
	 */
	public static EventFactory shuffleLibrary(SetGenerator whoseLibrary, String effectName)
	{
		EventFactory ret = new EventFactory(EventType.SHUFFLE_LIBRARY, effectName);
		ret.parameters.put(EventType.Parameter.CAUSE, This.instance());
		ret.parameters.put(EventType.Parameter.PLAYER, whoseLibrary);
		return ret;
	}

	/**
	 * Creates an {@link EventFactory} that shuffles your library.
	 * 
	 * @param effectName The text of the effect.
	 * @return The event factory shuffling your library.
	 */
	public static EventFactory shuffleYourLibrary(String effectName)
	{
		return shuffleLibrary(You.instance(), effectName);
	}

	public static EventFactory simultaneous(EventFactory... sequence)
	{
		java.util.List<EventFactory> events = java.util.Arrays.asList(sequence);
		String eventName = org.rnd.util.SeparatedList.get(" ", "", events).toString();

		return simultaneous(eventName, sequence);
	}

	public static EventFactory simultaneous(String eventName, EventFactory... sequence)
	{
		java.util.List<EventFactory> events = java.util.Arrays.asList(sequence);

		EventFactory ret = new EventFactory(SEQUENCE, eventName);
		ret.parameters.put(EventType.Parameter.EFFECT, Identity.instance((Object)events));
		ret.parameters.put(EventType.Parameter.EVENT, Empty.instance());
		return ret;
	}

	/**
	 * Create an effect dealing damage to objects or players from a spell.
	 * 
	 * @param amount The amount of damage to deal
	 * @param taker Who is taking damage
	 * @param effectName The text of the effect
	 */
	public static EventFactory spellDealDamage(int amount, SetGenerator taker, String effectName)
	{
		return spellDealDamage(numberGenerator(amount), taker, effectName);
	}

	/**
	 * Create an effect dealing damage to objects or players from a spell.
	 * 
	 * @param amount The amount of damage to deal
	 * @param taker Who is taking damage
	 * @param effectName The text of the effect
	 */
	public static EventFactory spellDealDamage(SetGenerator amount, SetGenerator taker, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, effectName);
		factory.parameters.put(EventType.Parameter.SOURCE, This.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, amount);
		factory.parameters.put(EventType.Parameter.TAKER, taker);
		return factory;
	}

	public static SetPattern spellsAndAbilities()
	{
		if(spellsAndAbilitiesPattern == null)
		{
			spellsAndAbilitiesPattern = new SetPattern()
			{
				@Override
				public void freeze(GameState state, Identified thisObject)
				{
					// nothing to do here
				}

				@Override
				public boolean match(GameState state, Identified thisObject, Set set)
				{
					for(GameObject o: set.getAll(GameObject.class))
						if(o.isSpell() || o.isActivatedAbility() || o.isTriggeredAbility())
							return true;

					return false;
				}
			};
		}
		return spellsAndAbilitiesPattern;
	}

	public static EventPattern spiritcraft()
	{
		if(spiritcraftPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, HasSubType.instance(SubType.SPIRIT, SubType.ARCANE));
			spiritcraftPattern = new ImmutableEventPattern(pattern);
		}
		return spiritcraftPattern;
	}

	public static EventFactory stopRegen(GameObject source, SetGenerator objects, String effectName)
	{
		SimpleSetPattern objectPattern = new SimpleSetPattern(objects);

		SimpleEventPattern destroysACreature = new SimpleEventPattern(EventType.DESTROY_ONE_PERMANENT);
		destroysACreature.put(EventType.Parameter.CAUSE, IdentifiedWithID.instance(source.ID));
		destroysACreature.put(EventType.Parameter.PERMANENT, objectPattern);

		SimpleEventPattern regenerate = new SimpleEventPattern(EventType.REGENERATE);
		regenerate.put(EventType.Parameter.OBJECT, objectPattern);

		EventReplacementEffectStopper stopRegen = new EventReplacementEffectStopper(source, destroysACreature, regenerate);
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.STOP_REPLACEMENT_EFFECT);
		part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(stopRegen));

		return createFloatingEffect(effectName, part);
	}

	public static EventFactory takeExtraTurns(SetGenerator who, int number, String effectName)
	{
		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, who);
		parameters.put(EventType.Parameter.NUMBER, numberGenerator(number));
		return new EventFactory(EventType.TAKE_EXTRA_TURN, parameters, effectName);
	}

	/**
	 * Create an effect tapping an object.
	 * 
	 * @param what What to tap
	 * @param effectName Text of the effect
	 */
	public static EventFactory tap(SetGenerator what, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.TAP_PERMANENTS, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, what);
		return factory;
	}

	public static EventFactory tapOrUntap(SetGenerator object, String objectName)
	{
		EventFactory tap = tap(object, "Tap " + objectName + ".");

		EventFactory untap = untap(object, "Untap " + objectName + ".");

		EventFactory tapOrUntap = new EventFactory(EventType.CHOOSE_AND_PERFORM, "Tap or untap " + objectName + ".");
		tapOrUntap.parameters.put(EventType.Parameter.PLAYER, You.instance());
		tapOrUntap.parameters.put(EventType.Parameter.EVENT, Identity.instance(tap, untap));
		return tapOrUntap;
	}

	/**
	 * Create an EventPattern triggering whenever something is tapped for mana
	 * and causing isManaAbility() to return true if this trigger produces mana.
	 * 
	 * @param what A <code>SetPattern</code> that will match against the object
	 * being tapped. The pattern has to correctly match ghosts (with cards like
	 * <code>Geothermal Crevice</code>), so avoid using any SetPatterns that
	 * ignore ghosts (like <code>SimpleSetPattern(Permanents.instance())</code>)
	 */
	public static EventPattern tappedForMana(SetGenerator players, final SetPattern what)
	{
		SetPattern manaAbilities = new SetPattern()
		{
			@Override
			public void freeze(GameState state, Identified thisObject)
			{
				// Nothing to freeze
			}

			@Override
			public boolean match(GameState state, Identified thisObject, Set set)
			{
				// CAST_SPELL_OR_ACTIVATE_ABILITY.OBJECT is guaranteed to
				// only have one object in it.
				GameObject object = set.getOne(GameObject.class);
				if(!object.isManaAbility())
					return false;
				if(!object.isActivatedAbility())
					return false;

				ActivatedAbility ability = (ActivatedAbility)object;
				if(!ability.costsTap)
					return false;

				if(!what.match(state, thisObject, new Set(ability.getSource(state))))
					return false;

				return true;
			}
		};

		SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
		pattern.put(EventType.Parameter.OBJECT, manaAbilities);
		pattern.put(EventType.Parameter.PLAYER, players);
		pattern.matchesManaAbilities = true;
		return pattern;
	}

	/**
	 * Cover for ExtractTargets(ChosenTargetsFor(Identity(targets), This)).
	 * 
	 * @param targets The targets to extract the targeted objects/players/zones
	 * from.
	 */
	public static SetGenerator targetedBy(Target... targets)
	{
		return ExtractTargets.instance(ChosenTargetsFor.instance(Identity.instance((Object[])targets), This.instance()));
	}

	/**
	 * Cover for ChosenTargetsFor(Identity(targets), This)). This is for
	 * "distribute" effects, all of which expect the target objects themselves.
	 * 
	 * @param targets The targets over which to distribute the counters/damage.
	 */
	public static SetGenerator targetedDistribute(Target... targets)
	{
		return ChosenTargetsFor.instance(Identity.instance((Object[])targets), This.instance());
	}

	public static EventFactory transformThis(String cardName)
	{
		return transform(ABILITY_SOURCE_OF_THIS, "Transform " + cardName + ".");
	}

	public static EventFactory transform(SetGenerator who, String effectName)
	{
		EventFactory transform = new EventFactory(EventType.TRANSFORM_PERMANENT, effectName);
		transform.parameters.put(EventType.Parameter.OBJECT, who);
		return transform;
	}

	public static ContinuousEffect.Part unblockable(SetGenerator who)
	{
		ContinuousEffect.Part ret = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
		ret.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Blocking.instance(who)));
		return ret;
	}

	public static EventFactory unless(SetGenerator who, EventFactory doThis, EventFactory unlessThis, String effectName)
	{
		EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, effectName);
		// TODO : No idea what string to use here, and taking a second string to
		// this function for a name no one ever sees seems inane -RulesGuru
		effect.parameters.put(EventType.Parameter.IF, Identity.instance(playerMay(who, unlessThis, "")));
		effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(doThis));
		return effect;
	}

	/**
	 * Create an effect untapping an object.
	 * 
	 * @param what What to untap
	 * @param effectName Text of the effect
	 */
	public static EventFactory untap(SetGenerator what, String effectName)
	{
		EventFactory factory = new EventFactory(EventType.UNTAP_PERMANENTS, effectName);
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, what);
		return factory;
	}

	public static ZoneChangePattern whenAnotherCreatureDies()
	{
		if(whenAnotherCreatureIsPutIntoAGraveyardFromTheBattlefieldPattern == null)
			whenAnotherCreatureIsPutIntoAGraveyardFromTheBattlefieldPattern = new ImmutableZoneChangePattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS), true));
		return whenAnotherCreatureIsPutIntoAGraveyardFromTheBattlefieldPattern;
	}

	public static ZoneChangePattern whenAPlayerCyclesACard()
	{
		if(null == whenAPlayerCyclesACardPattern)
		{
			// "When you cycle X" == "When you discard X to pay a cycling cost"
			whenAPlayerCyclesACardPattern = new ZoneChangePattern()
			{
				@Override
				public boolean looksBackInTime()
				{
					return false;
				}

				@Override
				public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state)
				{
					if(!zoneChange.isDiscard)
						return false;
					if(!zoneChange.isCost)
						return false;
					if(0 == zoneChange.causeID)
						return false;
					if(!state.containsIdentified(zoneChange.causeID))
						return false;

					GameObject cause = state.get(zoneChange.causeID);
					if(!(cause instanceof org.rnd.jmagic.abilities.keywords.CyclingBase.CyclingAbilityBase<?>))
						return false;

					return true;
				}
			};
		}
		return whenAPlayerCyclesACardPattern;
	}

	public static DamagePattern whenDealsCombatDamage(SetGenerator dealers)
	{
		return new SimpleDamagePattern(dealers, null, true);
	}

	public static DamagePattern whenDealsCombatDamageToACreature(SetGenerator dealers)
	{
		return new SimpleDamagePattern(dealers, CreaturePermanents.instance(), true);
	}

	public static DamagePattern whenDealsCombatDamageToAnOpponent(SetGenerator dealers)
	{
		return new SimpleDamagePattern(dealers, OpponentsOf.instance(You.instance()), true);
	}

	public static DamagePattern whenDealsCombatDamageToAPlayer(SetGenerator dealers)
	{
		return new SimpleDamagePattern(dealers, Players.instance(), true);
	}

	public static DamagePattern whenDealsDamage(SetGenerator dealers)
	{
		return new SimpleDamagePattern(dealers, null);
	}

	public static DamagePattern whenDealsDamageToACreature(SetGenerator dealers)
	{
		return new SimpleDamagePattern(dealers, CreaturePermanents.instance());
	}

	public static DamagePattern whenDealsDamageToAnOpponent(SetGenerator dealers)
	{
		return new SimpleDamagePattern(dealers, OpponentsOf.instance(You.instance()));
	}

	public static DamagePattern whenDealsDamageToAPlayer(SetGenerator dealers)
	{
		return new SimpleDamagePattern(dealers, Players.instance());
	}

	public static DamagePattern whenIsDealtCombatDamage(SetGenerator takers)
	{
		return new SimpleDamagePattern(null, takers, true);
	}

	public static DamagePattern whenIsDealtDamage(SetGenerator takers)
	{
		return new SimpleDamagePattern(null, takers);
	}

	public static DamagePattern whenIsDealtNoncombatDamage(SetGenerator takers)
	{
		return new NoncombatDamagePattern(new SimpleDamagePattern(null, takers));
	}

	public static ZoneChangePattern whenSomethingIsChampioned(final SetGenerator championing, final SetGenerator championed)
	{
		return new ZoneChangePattern()
		{
			@Override
			public boolean looksBackInTime()
			{
				// it's a leaves play trigger
				return true;
			}

			@Override
			public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state)
			{
				if(zoneChange.destinationZoneID != state.exileZone().ID)
					return false;

				GameObject cause = state.get(zoneChange.causeID);
				if(!(cause instanceof org.rnd.jmagic.abilities.keywords.Champion.ChampionExileAbility))
					return false;

				TriggeredAbility ability = (TriggeredAbility)cause;
				if(!championing.evaluate(state, thisObject).contains(ability.getSource(state)))
					return false;

				if(!championed.evaluate(state, thisObject).contains(state.get(zoneChange.oldObjectID)))
					return false;

				return true;
			}
		};
	}

	public static EventPattern whenThisAttacks()
	{
		if(whenThisAttacksPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			whenThisAttacksPattern = new ImmutableEventPattern(pattern);
		}
		return whenThisAttacksPattern;
	}

	public static EventPattern whenThisBecomesMonstrous()
	{
		if(whenThisBecomesMonstrosPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_MONSTROUS);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			whenThisBecomesMonstrosPattern = new ImmutableEventPattern(pattern);
		}
		return whenThisBecomesMonstrosPattern;
	}

	public static EventPattern whenThisBlocks()
	{
		if(whenThisBlocksPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			whenThisBlocksPattern = new ImmutableEventPattern(pattern);
		}
		return whenThisBlocksPattern;
	}

	public static DamagePattern whenThisDealsCombatDamageToAPlayer()
	{
		return new SimpleDamagePattern(ABILITY_SOURCE_OF_THIS, Players.instance(), true);
	}

	public static ZoneChangePattern whenThisDies()
	{
		return whenThisIsPutIntoAGraveyardFromTheBattlefield();
	}

	public static ZoneChangePattern whenThisEntersTheBattlefield()
	{
		if(whenThisEntersTheBattlefieldPattern == null)
			whenThisEntersTheBattlefieldPattern = new ImmutableZoneChangePattern(new SimpleZoneChangePattern(null, Battlefield.instance(), ABILITY_SOURCE_OF_THIS, false));
		return whenThisEntersTheBattlefieldPattern;
	}

	public static DamagePattern whenThisIsDealtDamage()
	{
		return whenIsDealtDamage(ABILITY_SOURCE_OF_THIS);
	}

	public static ZoneChangePattern whenThisIsPutIntoAGraveyardFromTheBattlefield()
	{
		if(whenThisIsPutIntoAGraveyardFromTheBattlefieldPattern == null)
			whenThisIsPutIntoAGraveyardFromTheBattlefieldPattern = new ImmutableZoneChangePattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), ABILITY_SOURCE_OF_THIS, true));
		return whenThisIsPutIntoAGraveyardFromTheBattlefieldPattern;
	}

	public static ZoneChangePattern whenThisIsPutIntoYourGraveyardFromTheBattlefield()
	{
		if(whenThisIsPutIntoYourGraveyardFromTheBattlefieldPattern == null)
			whenThisIsPutIntoYourGraveyardFromTheBattlefieldPattern = new ImmutableZoneChangePattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(You.instance()), ABILITY_SOURCE_OF_THIS, true));
		return whenThisIsPutIntoYourGraveyardFromTheBattlefieldPattern;
	}

	public static EventPattern whenThisIsTurnedFaceUp()
	{
		if(whenThisIsTurnedFaceUpPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.TURN_PERMANENT_FACE_UP);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			whenThisIsTurnedFaceUpPattern = new ImmutableEventPattern(pattern);
		}
		return whenThisIsTurnedFaceUpPattern;
	}

	public static ZoneChangePattern whenThisLeavesTheBattlefield()
	{
		if(whenThisLeavesTheBattlefieldPattern == null)
			whenThisLeavesTheBattlefieldPattern = new ImmutableZoneChangePattern(new SimpleZoneChangePattern(Battlefield.instance(), null, ABILITY_SOURCE_OF_THIS, true));
		return whenThisLeavesTheBattlefieldPattern;
	}

	public static EventPattern whenTimeCounterIsRemovedFromThis()
	{
		if(whenTimeCounterIsRemovedFromThisPattern == null)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.REMOVE_ONE_COUNTER);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			pattern.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.TIME));
			whenTimeCounterIsRemovedFromThisPattern = new ImmutableEventPattern(pattern);
		}
		return whenTimeCounterIsRemovedFromThisPattern;
	}

	public static EventPattern whenXAttacks(SetGenerator X)
	{
		SimpleEventPattern ret = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
		ret.put(EventType.Parameter.OBJECT, X);
		return ret;
	}

	public static ZoneChangePattern whenXDies(SetGenerator X)
	{
		return whenXIsPutIntoAGraveyardFromTheBattlefield(X);
	}

	public static ZoneChangePattern whenXIsPutIntoAGraveyardFromTheBattlefield(SetGenerator X)
	{
		return new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), X, true);
	}

	public static EventPattern whenYouCastANoncreatureSpell()
	{
		if(null == whenYouCastANoncreatureSpellPattern)
		{
			MultipleSetPattern noncreatureSpell = new MultipleSetPattern(true);
			noncreatureSpell.addPattern(new SimpleSetPattern(Spells.instance()));
			noncreatureSpell.addPattern(new NonTypePattern(Type.CREATURE));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, noncreatureSpell);
			whenYouCastANoncreatureSpellPattern = new ImmutableEventPattern(pattern);
		}
		return whenYouCastANoncreatureSpellPattern;
	}

	public static EventPattern whenYouCastASpell()
	{
		if(null == whenYouCastASpellPattern)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			whenYouCastASpellPattern = new ImmutableEventPattern(pattern);
		}
		return whenYouCastASpellPattern;
	}

	public static EventPattern whenYouCastASpellFromYourGraveyard()
	{
		if(null == whenYouCastASpellFromYourGraveyardPattern)
		{
			whenYouCastASpellFromYourGraveyardPattern = new EventPattern()
			{
				@Override
				public boolean match(Event event, Identified object, GameState state)
				{
					if(event.type != EventType.BECOMES_PLAYED)
						return false;

					GameObject played = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, object).getOne(GameObject.class);
					if(!played.isSpell())
						return false;

					EventTriggeredAbility triggeredAbility = (EventTriggeredAbility)object;
					GameObject source = (GameObject)(triggeredAbility.getSource(state));
					Player player = event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, object).getOne(Player.class);
					if(!source.getController(state).equals(player))
						return false;

					GameObject previous = state.get(played.pastSelf);
					return previous.getZone().isGraveyard();
				}

				@Override
				public boolean looksBackInTime()
				{
					return false;
				}

				@Override
				public boolean matchesManaAbilities()
				{
					return false;
				}
			};
		}
		return whenYouCastASpellFromYourGraveyardPattern;
	}

	public static EventPattern whenYouCastThisSpell()
	{
		if(null == whenYouCastThisSpellPattern)
		{
			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(ABILITY_SOURCE_OF_THIS);
			whenYouCastThisSpellPattern = new ImmutableEventPattern(pattern);
		}
		return whenYouCastThisSpellPattern;
	}

	/**
	 * Create a ZoneChangePattern for whenever you cycle this. Note that you
	 * should set "this.canTrigger" to "NonEmpty.instance()" so it triggers
	 * properly.
	 */
	public static ZoneChangePattern whenYouCycleThis()
	{
		if(null == whenYouCycleThisPattern)
		{
			// "When you cycle X" == "When you discard X to pay a cycling cost"
			whenYouCycleThisPattern = new ZoneChangePattern()
			{
				@Override
				public boolean looksBackInTime()
				{
					return false;
				}

				@Override
				public boolean match(ZoneChange zoneChange, Identified thisObject, GameState state)
				{
					if(!zoneChange.isDiscard)
						return false;
					if(!zoneChange.isCost)
						return false;
					if(0 == zoneChange.causeID)
						return false;
					if(!state.containsIdentified(zoneChange.causeID))
						return false;
					if(ABILITY_SOURCE_OF_THIS.evaluate(state, thisObject).getOne(GameObject.class).ID != zoneChange.newObjectID)
						return false;
					// Only "you" can discard "this", so "you" doesn't have to
					// be checked explicitly

					GameObject cause = state.get(zoneChange.causeID);
					if(!(cause instanceof org.rnd.jmagic.abilities.keywords.CyclingBase.CyclingAbilityBase<?>))
						return false;

					return true;
				}
			};
		}
		return whenYouCycleThisPattern;
	}

	/**
	 * Is the same as {@link Convenience#youMay(EventFactory, String)}, except
	 * that the event name is automatically generated by converting the first
	 * character of the inner event name to lower case and prepending
	 * "You may ".
	 */
	public static EventFactory youMay(EventFactory event)
	{
		String newName = "You may " + event.name.substring(0, 1).toLowerCase() + event.name.substring(1);
		return youMay(event, newName);
	}

	/**
	 * @return The result of
	 * {@link #playerMay(SetGenerator, EventFactory, String)
	 * playerMay(You.instance(), event, effectName)}
	 */
	public static EventFactory youMay(EventFactory event, String effectName)
	{
		return playerMay(You.instance(), event, effectName);
	}

	public static EventFactory youMayDrawACardIfYouDoDiscardACard()
	{
		return ifThen(youMay(drawACard()), discardCards(You.instance(), 1, "Discard a card."), "You may draw a card. If you do, discard a card.");
	}

	public static EventFactory youMayPay(String cost)
	{
		EventFactory factory = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay " + cost + ".");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool(cost)));
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		return factory;
	}

	public static EventFactory youMayPutAQuestCounterOnThis(String thisName)
	{
		EventFactory counterFactory = putCountersOnThis(1, Counter.CounterType.QUEST, thisName);
		EventFactory factory = youMay(counterFactory, ("You may put a quest counter on " + thisName + "."));
		return factory;
	}

	public static EventFactory youWinTheGame()
	{
		EventFactory win = new EventFactory(EventType.WIN_GAME, "You win the game.");
		win.parameters.put(EventType.Parameter.CAUSE, This.instance());
		win.parameters.put(EventType.Parameter.PLAYER, You.instance());
		return win;
	}

	/**
	 * @return An {@link EventFactory} of type {@link EventType#DISCARD_RANDOM}.
	 */
	public static EventFactory discardRandom(SetGenerator who, int num, String effectName)
	{
		EventFactory discardRandom = new EventFactory(EventType.DISCARD_RANDOM, effectName);
		discardRandom.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discardRandom.parameters.put(EventType.Parameter.PLAYER, who);
		discardRandom.parameters.put(EventType.Parameter.NUMBER, numberGenerator(num));
		return discardRandom;
	}
}
