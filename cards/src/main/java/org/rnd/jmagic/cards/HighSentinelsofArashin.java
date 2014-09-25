package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("High Sentinels of Arashin")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.SOLDIER})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class HighSentinelsofArashin extends Card
{
	public static final class HighSentinelsofArashinAbility1 extends StaticAbility
	{
		public HighSentinelsofArashinAbility1(GameState state)
		{
			super(state, "High Sentinels of Arashin gets +1/+1 for each other creature you control with a +1/+1 counter on it.");

			SetGenerator yourPumpedCreatures = Intersect.instance(HasCounterOfType.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE), CREATURES_YOU_CONTROL);
			SetGenerator otherPumpedCreatures = RelativeComplement.instance(yourPumpedCreatures, This.instance());
			SetGenerator amount = Count.instance(otherPumpedCreatures);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), amount, amount));
		}
	}

	public static final class HighSentinelsofArashinAbility2 extends ActivatedAbility
	{
		public HighSentinelsofArashinAbility2(GameState state)
		{
			super(state, "(3)(W): Put a +1/+1 counter on target creature.");
			this.setManaCost(new ManaPool("(3)(W)"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature."));
		}
	}

	public HighSentinelsofArashin(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// High Sentinels of Arashin gets +1/+1 for each other creature you
		// control with a +1/+1 counter on it.
		this.addAbility(new HighSentinelsofArashinAbility1(state));

		// (3)(W): Put a +1/+1 counter on target creature.
		this.addAbility(new HighSentinelsofArashinAbility2(state));
	}
}
