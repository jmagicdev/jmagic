package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Bloodflow Connoisseur")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class BloodflowConnoisseur extends Card
{
	public static final class BloodflowConnoisseurAbility0 extends ActivatedAbility
	{
		public BloodflowConnoisseurAbility0(GameState state)
		{
			super(state, "Sacrifice a creature: Put a +1/+1 counter on Bloodflow Connoisseur.");
			this.addCost(sacrificeACreature());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Bloodflow Connoisseur."));
		}
	}

	public BloodflowConnoisseur(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice a creature: Put a +1/+1 counter on Bloodflow Connoisseur.
		this.addAbility(new BloodflowConnoisseurAbility0(state));
	}
}
