package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rakshasa Vizier")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON, SubType.CAT})
@ManaCost("2BGU")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class RakshasaVizier extends Card
{
	public static final class ThingsBeingDelvedPattern implements SetPattern
	{
		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			Player you = You.instance().evaluate(state, thisObject).getOne(Player.class);
			int graveyard = you.getGraveyardID();
			int exileZone = state.exileZone().ID;
			for(ZoneChange zc: set.getAll(ZoneChange.class))
			{
				if(zc.sourceZoneID == graveyard && zc.destinationZoneID == exileZone)
					return true;
			}
			return false;
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// nothing to do
		}
	}

	public static final class ThingsBeingDelved extends SetGenerator
	{
		private SetGenerator zoneChanges;

		private ThingsBeingDelved(SetGenerator zoneChanges)
		{
			this.zoneChanges = zoneChanges;
		}

		public static SetGenerator instance(SetGenerator zoneChanges)
		{
			return new ThingsBeingDelved(zoneChanges);
		}

		public Set evaluate(GameState state, Identified thisObject)
		{
			Player you = You.instance().evaluate(state, thisObject).getOne(Player.class);
			int graveyard = you.getGraveyard(state).ID;
			int exileZone = state.exileZone().ID;

			Set ret = new Set();
			for(ZoneChange zc: zoneChanges.evaluate(state, thisObject).getAll(ZoneChange.class))
				if(zc.sourceZoneID == graveyard && zc.destinationZoneID == exileZone)
					ret.add(zc);

			return ret;
		}
	}

	public static final class RakshasaVizierAbility0 extends EventTriggeredAbility
	{
		public RakshasaVizierAbility0(GameState state)
		{
			super(state, "Whenever one or more cards are put into exile from your graveyard, put that many +1/+1 counters on Rakshasa Vizier.");

			// this doesn't use a zone change pattern, because zone change
			// patterns only match single zone changes.

			// so, i'm matching on MOVE_BATCH.

			// ... don't do this if you can help it.

			SimpleEventPattern delveThings = new SimpleEventPattern(EventType.MOVE_BATCH);
			delveThings.put(EventType.Parameter.TARGET, new ThingsBeingDelvedPattern());
			this.addPattern(delveThings);

			SetGenerator moves = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.TARGET);
			SetGenerator thatMany = Count.instance(ThingsBeingDelved.instance(moves));
			this.addEffect(putCounters(thatMany, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put that many +1/+1 counters on Rakshasa Vizier."));
		}
	}

	public RakshasaVizier(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Whenever one or more cards are put into exile from your graveyard,
		// put that many +1/+1 counters on Rakshasa Vizier.
		this.addAbility(new RakshasaVizierAbility0(state));
	}
}
