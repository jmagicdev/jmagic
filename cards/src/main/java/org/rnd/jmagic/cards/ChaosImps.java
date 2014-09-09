package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chaos Imps")
@Types({Type.CREATURE})
@SubTypes({SubType.IMP})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class ChaosImps extends Card
{
	public static final class ChaosImpsAbility2 extends StaticAbility
	{
		public ChaosImpsAbility2(GameState state)
		{
			super(state, "Chaos Imps has trample as long as it has a +1/+1 counter on it.");

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Trample.class));

			this.canApply = Both.instance(this.canApply, CountersOn.instance(This.instance(), Counter.CounterType.PLUS_ONE_PLUS_ONE));
		}
	}

	public ChaosImps(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));

		// Chaos Imps has trample as long as it has a +1/+1 counter on it.
		this.addAbility(new ChaosImpsAbility2(state));
	}
}
