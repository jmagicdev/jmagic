package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phyrexian Hydra")
@Types({Type.CREATURE})
@SubTypes({SubType.HYDRA})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class PhyrexianHydra extends Card
{
	// If damage would be dealt to Phyrexian Hydra, prevent that damage. Put
	// a -1/-1 counter on Phyrexian Hydra for each 1 damage prevented this
	// way.
	public static final class PreventDamageEffect extends DamageReplacementEffect
	{
		public PreventDamageEffect(Game game)
		{
			super(game, "If damage would be dealt to Phyrexian Hydra, prevent that damage. Put a -1/-1 counter on Phyrexian Hydra for each 1 damage prevented this way.");
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();

			for(DamageAssignment assignment: damageAssignments)
			{
				Identified taker = context.state.get(assignment.takerID);
				GameObject hydra = (GameObject)this.getStaticSourceObject(context.game.actualState);

				if(taker.equals(hydra))
					ret.add(assignment);
			}

			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			if(damageAssignments.isEmpty())
				return new java.util.LinkedList<EventFactory>();

			java.util.List<EventFactory> ret = new java.util.LinkedList<EventFactory>();
			SetGenerator sourceOfDamage = IdentifiedWithID.instance(damageAssignments.iterator().next().sourceID);

			EventFactory counterEvent = new EventFactory(EventType.PUT_COUNTERS, "Put a -1/-1 counter on Phyrexian Hydra for each 1 damage prevented this way.");
			counterEvent.parameters.put(EventType.Parameter.CAUSE, sourceOfDamage);
			counterEvent.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.MINUS_ONE_MINUS_ONE));
			counterEvent.parameters.put(EventType.Parameter.NUMBER, numberGenerator(damageAssignments.size()));
			counterEvent.parameters.put(EventType.Parameter.OBJECT, IdentifiedWithID.instance(damageAssignments.iterator().next().takerID));
			ret.add(counterEvent);

			damageAssignments.clear();
			return ret;
		}
	}

	public static final class PhyrexianHydraAbility1 extends StaticAbility
	{
		public PhyrexianHydraAbility1(GameState state)
		{
			super(state, "If damage would be dealt to Phyrexian Hydra, prevent that damage. Put a -1/-1 counter on Phyrexian Hydra for each 1 damage prevented this way.");

			this.addEffectPart(replacementEffectPart(new PreventDamageEffect(state.game)));
		}
	}

	public PhyrexianHydra(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// If damage would be dealt to Phyrexian Hydra, prevent that damage. Put
		// a -1/-1 counter on Phyrexian Hydra for each 1 damage prevented this
		// way.
		this.addAbility(new PhyrexianHydraAbility1(state));
	}
}
