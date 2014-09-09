package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Fastbond")
@Types({Type.ENCHANTMENT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class Fastbond extends Card
{
	public static class FastbondTracker extends Tracker<java.util.Map<Integer, Integer>>
	{
		// keys are player IDs, values are the first land that player played
		// this turn
		private java.util.HashMap<Integer, Integer> value = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.value);

		@SuppressWarnings("unchecked")
		@Override
		public FastbondTracker clone()
		{
			FastbondTracker ret = (FastbondTracker)super.clone();
			ret.value = (java.util.HashMap<Integer, Integer>)this.value.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.value);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected void reset()
		{
			this.value.clear();
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == EventType.PLAY_LAND;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			int player = event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID;
			if(this.value.containsKey(player))
				return;

			int played = event.getResult().getOne(ZoneChange.class).oldObjectID;
			this.value.put(player, played);
		}
	}

	public static final class FirstLandPlayed extends SetGenerator
	{
		public static SetGenerator instance(SetGenerator who)
		{
			return new FirstLandPlayed(who);
		}

		private SetGenerator who;

		private FirstLandPlayed(SetGenerator who)
		{
			this.who = who;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			FastbondTracker tracker = state.getTracker(FastbondTracker.class);
			java.util.Map<Integer, Integer> trackerValue = tracker.getValue(state);
			for(Player p: this.who.evaluate(state, thisObject).getAll(Player.class))
				if(trackerValue.containsKey(p.ID))
					ret.add(state.get(trackerValue.get(p.ID)));
			return ret;
		}
	}

	public static final class FastbondAbility1 extends EventTriggeredAbility
	{
		public FastbondAbility1(GameState state)
		{
			super(state, "Whenever you play a land, if it wasn't the first land you played this turn, Fastbond deals 1 damage to you.");

			SimpleEventPattern whenYouPlayALand = new SimpleEventPattern(EventType.PLAY_LAND);
			whenYouPlayALand.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(whenYouPlayALand);

			state.ensureTracker(new FastbondTracker());
			SetGenerator played = OldObjectOf.instance(EventResult.instance(TriggerEvent.instance(This.instance())));
			SetGenerator firstLand = FirstLandPlayed.instance(You.instance());
			this.interveningIf = RelativeComplement.instance(played, firstLand);

			this.addEffect(permanentDealDamage(1, You.instance(), "Fastbond deals 1 damage to you."));
		}
	}

	public Fastbond(GameState state)
	{
		super(state);

		// You may play any number of additional lands on each of your turns.
		this.addAbility(new org.rnd.jmagic.abilities.PlayExtraLands.Final(this.state, null, "You may play any number of lands on each of your turns."));

		// Whenever you play a land, if it wasn't the first land you played this
		// turn, Fastbond deals 1 damage to you.
		this.addAbility(new FastbondAbility1(state));
	}
}
