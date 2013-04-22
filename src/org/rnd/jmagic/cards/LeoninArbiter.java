package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Leonin Arbiter")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.CAT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class LeoninArbiter extends Card
{
	public static final class ActionNotTaken extends Tracker<java.util.Set<Integer>>
	{
		private java.util.Set<Integer> values = new java.util.HashSet<Integer>();
		private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

		@Override
		protected ActionNotTaken clone()
		{
			ActionNotTaken ret = (ActionNotTaken)super.clone();
			ret.values = new java.util.HashSet<Integer>(this.values);
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
			return ret;
		}

		@Override
		protected java.util.Set<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void reset()
		{
			this.values.clear();
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return true;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			// intentionally left blank
		}

		protected void update(GameState state, Player player)
		{
			// call register to handle the reset if need be
			this.register(state, null);
			this.values.add(player.ID);
		}
	}

	public static final class PlayersWhoHavePaid extends SetGenerator
	{
		private ActionNotTaken tracker;

		private PlayersWhoHavePaid(ActionNotTaken tracker)
		{
			this.tracker = tracker;
		}

		public static SetGenerator instance(ActionNotTaken tracker)
		{
			return new PlayersWhoHavePaid(tracker);
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			java.util.Set<Integer> playerIDs = this.tracker.getValue(state);
			Set ret = new Set();
			for(int ID: playerIDs)
				ret.add(state.get(ID));
			return ret;
		}
	}

	public static final class IgnoreAction extends PlayerAction
	{
		private ActionNotTaken tracker;

		public IgnoreAction(Game game, Player player, int sourceID, ActionNotTaken tracker)
		{
			super(game, "Pay (2). Ignore Leonin Arbiter's effect until end of turn.", player, sourceID);
			this.tracker = tracker;
		}

		@Override
		public int getSourceObjectID()
		{
			return this.sourceID;
		}

		@Override
		public boolean perform()
		{
			Player actor = this.actor();

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (2)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.PLAYER, Identity.instance(actor));
			pay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("2")));
			boolean result = pay.createEvent(this.game, this.game.actualState.getByIDObject(this.getSourceObjectID())).perform(null, true);

			if(result)
				this.tracker.update(this.game.actualState, actor);

			return result;
		}

		@Override
		public PlayerInterface.ReversionParameters getReversionReason()
		{
			Player player = this.game.physicalState.get(this.actorID);
			return new PlayerInterface.ReversionParameters("LeoninArbiterIgnoreAction", player.getName() + " failed to ignore Leonin Arbiter.");
		}
	}

	public static final class LeoninArbiterAbility0 extends StaticAbility
	{
		private ActionNotTaken tracker;

		public final class IgnoreActionFactory extends SpecialActionFactory
		{
			@Override
			public java.util.Set<PlayerAction> getActions(GameState state, GameObject source, Player actor)
			{
				return java.util.Collections.<PlayerAction>singleton(new IgnoreAction(state.game, actor, source.ID, LeoninArbiterAbility0.this.tracker));
			}
		}

		public LeoninArbiterAbility0(GameState state)
		{
			super(state, "Players can't search libraries. Any player may pay (2) for that player to ignore this effect until end of turn.");
			this.tracker = new ActionNotTaken();

			SetGenerator players = RelativeComplement.instance(Players.instance(), PlayersWhoHavePaid.instance(this.tracker));
			SimpleEventPattern searching = new SimpleEventPattern(EventType.SEARCH_MARKER);
			searching.put(EventType.Parameter.PLAYER, players);
			searching.put(EventType.Parameter.CARD, LibraryOf.instance(Players.instance()));

			ContinuousEffect.Part prohibit = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibit.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(searching));
			this.addEffectPart(prohibit);

			ContinuousEffect.Part action = new ContinuousEffect.Part(ContinuousEffectType.SPECIAL_ACTION);
			action.parameters.put(ContinuousEffectType.Parameter.PLAYER, players);
			action.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(new IgnoreActionFactory()));
			this.addEffectPart(action);
		}
	}

	public LeoninArbiter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Players can't search libraries. Any player may pay (2) for that
		// player to ignore this effect until end of turn.
		this.addAbility(new LeoninArbiterAbility0(state));
	}
}
