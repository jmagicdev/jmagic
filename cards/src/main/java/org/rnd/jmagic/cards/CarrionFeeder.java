package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Carrion Feeder")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Scourge.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class CarrionFeeder extends Card
{
	public static final class CarrionFeederAbility1 extends ActivatedAbility
	{
		public CarrionFeederAbility1(GameState state)
		{
			super(state, "Sacrifice a creature: Put a +1/+1 counter on Carrion Feeder.");
			this.addCost(sacrificeACreature());
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Carrion Feeder"));
		}
	}

	public CarrionFeeder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Carrion Feeder can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, "Carrion Feeder"));

		// Sacrifice a creature: Put a +1/+1 counter on Carrion Feeder.
		this.addAbility(new CarrionFeederAbility1(state));
	}
}
