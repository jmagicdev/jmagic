package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Scarland Thrinax")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("BRG")
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class ScarlandThrinax extends Card
{
	public static final class ScarlandThrinaxAbility0 extends ActivatedAbility
	{
		public ScarlandThrinaxAbility0(GameState state)
		{
			super(state, "Sacrifice a creature: Put a +1/+1 counter on Scarland Thrinax.");
			this.addCost(sacrificeACreature());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Scarland Thrinax"));
		}
	}

	public ScarlandThrinax(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Sacrifice a creature: Put a +1/+1 counter on Scarland Thrinax.
		this.addAbility(new ScarlandThrinaxAbility0(state));
	}
}
