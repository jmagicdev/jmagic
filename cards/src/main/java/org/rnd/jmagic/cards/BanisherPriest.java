package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Banisher Priest")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2014, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class BanisherPriest extends Card
{
	public static class LTBTracker extends Tracker<java.util.Set<Integer>>
	{
		private java.util.HashSet<Integer> IDs = new java.util.HashSet<Integer>();
		private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(IDs);

		@Override
		public LTBTracker clone()
		{
			LTBTracker ret = (LTBTracker)super.clone();
			ret.IDs = new java.util.HashSet<Integer>();
			ret.IDs.addAll(this.IDs);
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.IDs);
			return ret;
		}

		@Override
		protected java.util.Set<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type != EventType.MOVE_BATCH)
				return false;

			for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
			{
				if(state.battlefield().ID != change.sourceZoneID)
					continue;

				return true;
			}

			return false;
		}

		@Override
		protected void reset()
		{
			this.IDs.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			for(ZoneChange change: event.parametersNow.get(EventType.Parameter.TARGET).evaluate(state, null).getAll(ZoneChange.class))
			{
				if(state.battlefield().ID == change.sourceZoneID)
					this.IDs.add(change.oldObjectID);
			}
		}
	}

	public static class LeftTheBattlefield extends SetGenerator
	{
		public static SetGenerator instance()
		{
			return _instance;
		}

		private static SetGenerator _instance = new LeftTheBattlefield();

		private LeftTheBattlefield()
		{
			// singleton generator
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			for(int ID: state.getTracker(LTBTracker.class).getValue(state))
				ret.add(state.get(ID));
			return ret;
		}

	}
	
	public static class ETBExile extends EventTriggeredAbility
	{
		public ETBExile(GameState state)
		{
			super(state, "When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			
			SetGenerator opponentsCreatures = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			Target target = this.addTarget(opponentsCreatures, "target creature an opponent controls");

			state.ensureTracker(new LTBTracker());
			SetGenerator thisIsGone = Intersect.instance(ABILITY_SOURCE_OF_THIS, LeftTheBattlefield.instance());

			EventFactory exileUntil = new EventFactory(EventType.EXILE_UNTIL, "Exile target creature an opponent controls until Banisher Priest leaves the battlefield.");
			exileUntil.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileUntil.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			exileUntil.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(thisIsGone));
			this.addEffect(exileUntil);
		}
	}

	public BanisherPriest(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new ETBExile(state));
	}
}
