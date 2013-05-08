package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flesh Allergy")
@Types({Type.SORCERY})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class FleshAllergy extends Card
{
	public static final class CreaturesKilledGenerator extends SetGenerator
	{
		private static CreaturesKilledGenerator _instance = null;

		public static CreaturesKilledGenerator instance()
		{
			if(_instance == null)
				_instance = new CreaturesKilledGenerator();
			return _instance;
		}

		private CreaturesKilledGenerator()
		{
			// Singleton Generator
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			return IdentifiedWithID.instance(state.getTracker(CreaturesKilled.class).getValue(state)).evaluate(state, thisObject);
		}

		public static final class CreaturesKilled extends Tracker<java.util.Set<Integer>>
		{

			private java.util.HashSet<Integer> IDs = new java.util.HashSet<Integer>();
			private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.IDs);

			@Override
			public CreaturesKilled clone()
			{
				CreaturesKilled ret = (CreaturesKilled)super.clone();
				ret.IDs = new java.util.HashSet<Integer>(this.IDs);
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

					if(!state.<GameObject>get(change.oldObjectID).getTypes().contains(Type.CREATURE))
						continue;

					Zone to = state.get(change.destinationZoneID);
					if(!to.isGraveyard())
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
					if(state.battlefield().ID != change.sourceZoneID)
						continue;

					if(!state.<GameObject>get(change.oldObjectID).getTypes().contains(Type.CREATURE))
						continue;

					Zone to = state.get(change.destinationZoneID);
					if(!to.isGraveyard())
						continue;

					this.IDs.add(change.newObjectID);
				}
			}
		}
	}

	public FleshAllergy(GameState state)
	{
		super(state);

		// As an additional cost to cast Flesh Allergy, sacrifice a creature.
		this.addCost(sacrifice(You.instance(), 1, CreaturePermanents.instance(), "sacrifice a creature"));

		state.ensureTracker(new CreaturesKilledGenerator.CreaturesKilled());

		// Destroy target creature. Its controller loses life equal to the
		// number of creatures put into all graveyards from the battlefield this
		// turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(destroy(target, "Destroy target creature."));
		this.addEffect(loseLife(ControllerOf.instance(target), Count.instance(CreaturesKilledGenerator.instance()), "Its controller loses life equal to the number of creatures put into all graveyards from the battlefield this turn."));
	}
}
