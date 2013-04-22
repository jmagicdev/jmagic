package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unbreathing Horde")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class UnbreathingHorde extends Card
{
	public static final class UnbreathingHordeAbility1 extends StaticAbility
	{
		public static final class PreventDamageEffect extends DamageReplacementEffect
		{
			public PreventDamageEffect(Game game)
			{
				super(game, "If Unbreathing Horde would be dealt damage, prevent that damage and remove a +1/+1 counter from it.");
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				DamageAssignment.Batch ret = new DamageAssignment.Batch();

				int horde = this.getStaticSourceObject(context.game.actualState).ID;
				for(DamageAssignment assignment: damageAssignments)
					if(assignment.takerID == horde)
						ret.add(assignment);

				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				damageAssignments.clear();
				return new java.util.LinkedList<EventFactory>();
			}

			@Override
			public java.util.List<EventFactory> replace(DamageAssignment.Batch damageAssignments)
			{
				if(damageAssignments.isEmpty())
					return new java.util.LinkedList<EventFactory>();

				SetGenerator thisThing = Identity.instance(this.getStaticSourceObject(this.game.physicalState));
				EventFactory effect = new EventFactory(EventType.REMOVE_COUNTERS, "Remove a +1/+1 counter from it");
				effect.parameters.put(EventType.Parameter.CAUSE, IdentifiedWithID.instance(damageAssignments.iterator().next().sourceID));
				effect.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
				effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
				effect.parameters.put(EventType.Parameter.OBJECT, thisThing);
				return java.util.Collections.singletonList(effect);
			}
		}

		public UnbreathingHordeAbility1(GameState state)
		{
			super(state, "If Unbreathing Horde would be dealt damage, prevent that damage and remove a +1/+1 counter from it.");

			this.addEffectPart(replacementEffectPart(new PreventDamageEffect(state.game)));
		}
	}

	public UnbreathingHorde(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Unbreathing Horde enters the battlefield with a +1/+1 counter on it
		// for each other Zombie you control and each Zombie card in your
		// graveyard.
		SetGenerator zombies = HasSubType.instance(SubType.ZOMBIE);
		SetGenerator youControl = ControlledBy.instance(You.instance());
		SetGenerator inYourYard = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator number = Count.instance(Intersect.instance(zombies, Union.instance(youControl, inYourYard)));
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), number, "a +1/+1 counter on it for each other Zombie you control and each Zombie card in your graveyard", Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// If Unbreathing Horde would be dealt damage, prevent that damage and
		// remove a +1/+1 counter from it.
		this.addAbility(new UnbreathingHordeAbility1(state));
	}
}
