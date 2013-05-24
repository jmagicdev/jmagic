package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vigor")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.INCARNATION})
@ManaCost("3GGG")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Vigor extends Card
{
	// If damage would be dealt to a creature you control other than Vigor,
	// prevent that damage. Put a +1/+1 counter on that creature for each 1
	// damage prevented this way.
	public static final class PreventDamageEffect extends DamageReplacementEffect
	{
		public PreventDamageEffect(Game game)
		{
			super(game, "If damage would be dealt to a creature you control other than Vigor, prevent that damage. Put a +1/+1 counter on that creature for each 1 damage prevented this way.");
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();

			for(DamageAssignment assignment: damageAssignments)
			{
				// a creature ...
				Identified taker = context.state.get(assignment.takerID);
				if(!(taker.isGameObject()))
					continue;
				GameObject takerObject = (GameObject)taker;
				if(!takerObject.getTypes().contains(Type.CREATURE))
					continue;

				// ... you control ...
				GameObject vigor = (GameObject)this.getStaticSourceObject(context.game.actualState);
				if(takerObject.controllerID != vigor.controllerID)
					continue;

				// ... other than Vigor
				if(taker.equals(vigor))
					continue;

				ret.add(assignment);
			}

			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			if(damageAssignments.isEmpty())
				return new java.util.LinkedList<EventFactory>();

			// This maps from GameObject IDs to the number of counters to put on
			// that GameObject
			java.util.Map<Integer, Integer> countersToPlace = new java.util.HashMap<Integer, Integer>();

			for(DamageAssignment damage: damageAssignments)
			{
				if(!countersToPlace.containsKey(damage.takerID))
					countersToPlace.put(damage.takerID, 1);
				else
					countersToPlace.put(damage.takerID, countersToPlace.get(damage.takerID) + 1);
			}

			java.util.List<EventFactory> ret = new java.util.LinkedList<EventFactory>();
			SetGenerator sourceOfDamage = IdentifiedWithID.instance(damageAssignments.iterator().next().sourceID);
			for(java.util.Map.Entry<Integer, Integer> counterToPlace: countersToPlace.entrySet())
			{
				EventFactory counterEvent = new EventFactory(EventType.PUT_COUNTERS, "Put a +1/+1 counter on that creature for each 1 damage prevented this way");
				counterEvent.parameters.put(EventType.Parameter.CAUSE, sourceOfDamage);
				counterEvent.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
				counterEvent.parameters.put(EventType.Parameter.NUMBER, numberGenerator(counterToPlace.getValue()));
				counterEvent.parameters.put(EventType.Parameter.OBJECT, IdentifiedWithID.instance(counterToPlace.getKey()));
				ret.add(counterEvent);
			}

			damageAssignments.clear();
			return ret;
		}
	}

	public static final class Vigorous extends StaticAbility
	{
		public Vigorous(GameState state)
		{
			super(state, "If damage would be dealt to a creature you control other than Vigor, prevent that damage. Put a +1/+1 counter on that creature for each 1 damage prevented this way.");

			this.addEffectPart(replacementEffectPart(new PreventDamageEffect(state.game)));
		}
	}

	public Vigor(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new Vigorous(state));
		this.addAbility(new org.rnd.jmagic.abilities.LorwynIncarnationTrigger(state, this.getName()));
	}
}
