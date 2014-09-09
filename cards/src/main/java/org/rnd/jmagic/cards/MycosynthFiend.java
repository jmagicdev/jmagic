package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mycosynth Fiend")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class MycosynthFiend extends Card
{
	public static final class MycosynthFiendAbility0 extends StaticAbility
	{
		public MycosynthFiendAbility0(GameState state)
		{
			super(state, "Mycosynth Fiend gets +1/+1 for each poison counter your opponents have.");

			SetGenerator count = Count.instance(CountersOn.instance(OpponentsOf.instance(You.instance()), Counter.CounterType.POISON));

			this.addEffectPart(modifyPowerAndToughness(This.instance(), count, count));
		}
	}

	public MycosynthFiend(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Mycosynth Fiend gets +1/+1 for each poison counter your opponents
		// have.
		this.addAbility(new MycosynthFiendAbility0(state));
	}
}
