package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Simic Manipulator")
@Types({Type.CREATURE})
@SubTypes({SubType.MUTANT, SubType.WIZARD})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class SimicManipulator extends Card
{
	public static final class SimicManipulatorAbility1 extends ActivatedAbility
	{
		public SimicManipulatorAbility1(GameState state)
		{
			super(state, "(T), Remove one or more +1/+1 counters from Simic Manipulator: Gain control of target creature with power less than or equal to the number of +1/+1 counters removed this way.");
			this.costsTap = true;
			// Remove one or more +1/+1 counters from Simic Manipulator

			EventFactory removeCounters = new EventFactory(EventType.REMOVE_COUNTERS_CHOICE, "Remove one or more +1/+1 counters from Simic Manipulator");
			removeCounters.parameters.put(EventType.Parameter.CAUSE, This.instance());
			removeCounters.parameters.put(EventType.Parameter.COUNTER, CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.PLUS_ONE_PLUS_ONE));
			removeCounters.parameters.put(EventType.Parameter.NUMBER, Between.instance(1, null));
			removeCounters.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addCost(removeCounters);

			SetGenerator numCounters = Count.instance(CostChoice.instance(You.instance(), removeCounters));
			SetGenerator powerInRange = HasPower.instance(Between.instance(Empty.instance(), numCounters));
			SetGenerator legalTargets = Intersect.instance(CreaturePermanents.instance(), powerInRange);
			SetGenerator target = targetedBy(this.addTarget(legalTargets, "target creature with power less than or equal to the number of +1/+1 counters removed this way"));

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect(Empty.instance(), "Gain control of target creature with power less than or equal to the number of +1/+1 counters removed this way.", controlPart));
		}
	}

	public SimicManipulator(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));

		// (T), Remove one or more +1/+1 counters from Simic Manipulator: Gain
		// control of target creature with power less than or equal to the
		// number of +1/+1 counters removed this way.
		this.addAbility(new SimicManipulatorAbility1(state));
	}
}
