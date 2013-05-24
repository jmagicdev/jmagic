package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/**
 * 702.59. Suspend
 * 
 * 702.59a Suspend is a keyword that represents three abilities. The first is a
 * static ability that functions while the card with suspend is in a player's
 * hand. The second and third are triggered abilities that function in the exile
 * zone. "Suspend N -- [cost]" means "If you could begin to cast this card by
 * putting it onto the stack from your hand, you may pay [cost] and exile it
 * with N time counters on it. This action doesn't use the stack," and "At the
 * beginning of your upkeep, if this card is suspended, remove a time counter
 * from it," and "When the last time counter is removed from this card, if it's
 * exiled, play it without paying its mana cost if able. If you can't, it
 * remains exiled. If you cast a creature spell this way, it gains haste until
 * you lose control of the spell or the permanent it becomes."
 * 
 * 702.59b A card is "suspended" if it's in the exile zone, has suspend, and has
 * a time counter on it.
 * 
 * 702.59c Casting a spell as an effect of its suspend ability follows the rules
 * for paying alternative costs in rules 601.2b and 601.2e-g.
 */
public final class Suspend extends Keyword
{
	private final int suspendTime;
	private final String cost;

	/**
	 * @param state The state in which this keyword exists.
	 * @param suspendTime The number of counters to suspend the card with.
	 * @param suspendCost The cost to suspend the card.
	 */
	public Suspend(GameState state, int suspendTime, String suspendCost)
	{
		super(state, "Suspend " + suspendTime + "\u2014" + suspendCost);
		this.suspendTime = suspendTime;
		this.cost = suspendCost;
	}

	/**
	 * For use by {@link Suspend#X}. Sets the suspend time to -1; the suspend
	 * cost is assumed to have an X in it, and the number of counters to suspend
	 * the card with will be set at the time the player chooses a value for X.
	 */
	private Suspend(GameState state, String suspendCost)
	{
		super(state, "Suspend X\u2014" + suspendCost + ". X can't be 0.");
		this.suspendTime = -1;
		this.cost = suspendCost;
	}

	/**
	 * Represents "Suspend X--[cost]. X can't be 0."
	 * 
	 * @param state The state in which this keyword exists.
	 * @param suspendCost The cost to suspend the card. Must include an (X).
	 * @return the created ability
	 */
	public static Suspend X(GameState state, String suspendCost)
	{
		return new Suspend(state, suspendCost);
	}

	@Override
	public Suspend create(Game game)
	{
		return new Suspend(game.physicalState, this.suspendTime, this.cost);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.LinkedList<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new SuspendRemoveCounter(this.state));
		ret.add(new SuspendCastSpell(this.state));
		return ret;
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new SuspendStaticAction(this.state, this));
		return ret;
	}

	@Override
	public final boolean isSuspend()
	{
		return true;
	}

	public static final class SuspendCastSpell extends EventTriggeredAbility
	{
		public SuspendCastSpell(GameState state)
		{
			super(state, "When the last time counter is removed from this card, if it's exiled, play it without paying its mana cost if able. If you can't, it remains exiled. If you cast a creature spell this way, it gains haste until you lose control of the spell or the permanent it becomes.");
			this.canTrigger = NonEmpty.instance();

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.REMOVED_LAST_COUNTER);
			pattern.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.TIME));
			pattern.put(EventType.Parameter.OBJECT, thisCard);
			this.addPattern(pattern);

			this.interveningIf = Intersect.instance(InZone.instance(ExileZone.instance()), thisCard);

			EventFactory castFactory = new EventFactory(EventType.PLAY_CARD, "Play it without paying its mana cost.");
			castFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			castFactory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(This.instance()));
			castFactory.parameters.put(EventType.Parameter.ALTERNATE_COST, Empty.instance());
			castFactory.parameters.put(EventType.Parameter.OBJECT, thisCard);

			SetGenerator thatSpell = FutureSelf.instance(thisCard);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatSpell);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(Haste.class)));

			SetGenerator allObjectsYouControl = ControlledBy.instance(You.instance(), Union.instance(Stack.instance(), Battlefield.instance()));
			SetGenerator theSpellOrThePermanentItBecomes = Union.instance(thatSpell, Intersect.instance(Permanents.instance(), FutureSelf.instance(thatSpell)));
			EventFactory floatFactory = createFloatingEffect(Not.instance(Intersect.instance(allObjectsYouControl, theSpellOrThePermanentItBecomes)), "It gains haste until you lose control of the spell or the permanent it becomes.", part);

			EventFactory ifCreatureFactory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If you cast a creature spell this way, it gains haste until you lose control of the spell or the permanent it becomes.");
			ifCreatureFactory.parameters.put(EventType.Parameter.IF, Intersect.instance(HasType.instance(Type.CREATURE), thatSpell));
			ifCreatureFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(floatFactory));

			EventFactory ifEventFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "If you cast a creature spell this way, it gains haste until you lose control of the spell or the permanent it becomes.");
			ifEventFactory.parameters.put(EventType.Parameter.IF, Identity.instance(castFactory));
			ifEventFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(ifCreatureFactory));
			this.addEffect(ifEventFactory);
		}
	}

	public static final class SuspendRemoveCounter extends EventTriggeredAbility
	{
		public SuspendRemoveCounter(GameState state)
		{
			super(state, "At the beginning of your upkeep, if this card is suspended, remove a time counter from it.");
			this.canTrigger = NonEmpty.instance();

			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			this.interveningIf = Intersect.instance(Suspended.instance(), thisCard);

			this.addEffect(removeCountersFromThis(1, Counter.CounterType.TIME, "this card"));
		}
	}

	public static final class SuspendStaticAction extends StaticAbility
	{
		private final int parentID;

		public SuspendStaticAction(GameState state, Suspend parent)
		{
			super(state, "If you could begin to cast this card by putting it onto the stack from your hand, you may pay " + parent.cost + " and exile it with " + (parent.suspendTime == -1 ? "X" : parent.suspendTime) + " time counter" + (parent.suspendTime == 1 ? "" : "s") + " on it. This action doesn't use the stack.");
			this.parentID = parent.ID;

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SPECIAL_ACTION);
			part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(new SuspendAction.Factory(parent)));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, ControllerOf.instance(This.instance()));
			this.addEffectPart(part);

			// This can always apply
			this.canApply = NonEmpty.instance();
		}

		@Override
		public SuspendStaticAction create(Game game)
		{
			return new SuspendStaticAction(game.physicalState, game.physicalState.<Suspend>get(this.parentID));
		}
	}

	public static class SuspendAction extends PlayerAction
	{
		/**
		 * Warning: This class is very stateful.
		 */
		public static class Factory extends SpecialActionFactory
		{
			protected final int suspendTime;
			protected final int abilityID;

			public Factory(Suspend ability)
			{
				this.suspendTime = ability.suspendTime;
				this.abilityID = ability.ID;
			}

			@Override
			public java.util.Set<PlayerAction> getActions(GameState state, GameObject source, Player actor)
			{
				java.util.Set<PlayerAction> ret = new java.util.HashSet<PlayerAction>();

				// Can only suspend from hand
				if(!source.getZone().isHand())
					return ret;

				// And only when you could begin casting the spell
				boolean makeAbility = false;
				for(PlayerAction action: state.playerActions)
					if(action instanceof CastSpellAction)
						if(((CastSpellAction)action).toBePlayedID == source.ID)
						{
							makeAbility = true;
							break;
						}

				if(makeAbility)
					ret.add(getAction(state, source, actor));

				return ret;
			}

			private SuspendAction getAction(GameState state, GameObject source, Player actor)
			{
				return new SuspendAction(state.game, this.suspendTime, source, actor, this.abilityID, state.<Suspend>get(this.abilityID).cost);
			}
		}

		private final int suspendTime;
		private final int object;
		private final String suspendCost;

		public SuspendAction(Game game, int suspendTime, GameObject object, Player actor, int abilityID, String suspendCost)
		{
			super(game, "Suspend " + object, actor, abilityID);
			this.suspendTime = suspendTime;
			this.object = object.ID;
			this.suspendCost = suspendCost;
		}

		@Override
		public int getSourceObjectID()
		{
			return this.object;
		}

		@Override
		public boolean perform()
		{
			ManaPool cost = new ManaPool(this.suspendCost);
			int numCounters = this.suspendTime;
			Player who = this.actor();
			if(cost.usesX())
			{
				numCounters = who.chooseNumber(new org.rnd.util.NumberRange(1, null), "Choose a value for X.");
				cost = cost.expandX(numCounters, ManaSymbol.ManaType.COLORLESS);
			}

			EventFactory payFactory = new EventFactory(EventType.PAY_MANA, "Pay " + cost + ".");
			payFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			payFactory.parameters.put(EventType.Parameter.COST, Identity.instance(cost));
			payFactory.parameters.put(EventType.Parameter.PLAYER, Identity.instance(who));
			payFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));

			String eventName = "Pay " + this.suspendCost + " and exile it with " + numCounters + " time counter" + (this.suspendTime == 1 ? "" : "s") + " on it.";

			EventFactory suspend = new EventFactory(SUSPEND_EVENT, eventName);
			suspend.parameters.put(EventType.Parameter.NUMBER, Identity.instance(numCounters));
			suspend.parameters.put(EventType.Parameter.OBJECT, IdentifiedWithID.instance(this.object));

			EventFactory ifPaySuspend = new EventFactory(EventType.IF_EVENT_THEN_ELSE, eventName);
			ifPaySuspend.parameters.put(EventType.Parameter.IF, Identity.instance(payFactory));
			ifPaySuspend.parameters.put(EventType.Parameter.THEN, Identity.instance(suspend));
			Event theSuspendEvent = ifPaySuspend.createEvent(this.game, this.game.actualState.getByIDObject(this.getSourceObjectID()));
			return theSuspendEvent.perform(null, true);
		}

		/**
		 * @eparam NUMBER: the number of time counters to suspend it with
		 * @eparam OBJECT: the card to suspend
		 * @eparam RESULT: empty
		 */
		public static final EventType SUSPEND_EVENT = new EventType("SUSPEND_EVENT")
		{
			@Override
			public Parameter affects()
			{
				return Parameter.OBJECT;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				event.setResult(Empty.set);

				Set objectSet = parameters.get(Parameter.OBJECT);
				int number = Sum.get(parameters.get(Parameter.NUMBER));

				java.util.Map<Parameter, Set> exileParameters = new java.util.HashMap<Parameter, Set>();
				exileParameters.put(Parameter.CAUSE, new Set(game));
				exileParameters.put(Parameter.TO, new Set(game.actualState.exileZone()));
				exileParameters.put(Parameter.OBJECT, objectSet);
				Event putOntoBattlefield = createEvent(game, "Exile " + objectSet + ".", EventType.MOVE_OBJECTS, exileParameters);
				boolean moveStatus = putOntoBattlefield.perform(event, false);

				ZoneChange zc = putOntoBattlefield.getResult().getOne(ZoneChange.class);

				EventFactory factory = new EventFactory(PUT_COUNTERS, "Put " + number + " time counter" + (number == 1 ? "" : "s") + " on " + objectSet + ".");
				factory.parameters.put(Parameter.CAUSE, CurrentGame.instance());
				factory.parameters.put(Parameter.COUNTER, Identity.instance(Counter.CounterType.TIME));
				factory.parameters.put(Parameter.NUMBER, numberGenerator(number));
				factory.parameters.put(Parameter.OBJECT, NewObjectOf.instance(Identity.instance(zc)));
				zc.events.add(factory);

				return moveStatus;
			}
		};

		@Override
		public PlayerInterface.ReversionParameters getReversionReason()
		{
			Player player = this.game.physicalState.get(this.actorID);
			GameObject object = this.game.physicalState.get(this.object);
			return new PlayerInterface.ReversionParameters("SuspendAction", player.getName() + " failed to suspend " + object.getName() + ".");
		}
	}
}
