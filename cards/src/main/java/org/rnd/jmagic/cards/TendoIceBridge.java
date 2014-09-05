package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Tendo Ice Bridge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = BetrayersOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class TendoIceBridge extends Card
{
	public static final class TendoIceBridgeAbility2 extends ActivatedAbility
	{
		public TendoIceBridgeAbility2(GameState state)
		{
			super(state, "(T), Remove a charge counter from Tendo Ice Bridge: Add one mana of any color to your mana pool.");
			this.costsTap = true;
			this.addCost(removeCountersFromThis(1, Counter.CounterType.CHARGE, "Tendo Ice Bridge"));
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));
		}
	}

	public TendoIceBridge(GameState state)
	{
		super(state);

		// Tendo Ice Bridge enters the battlefield with a charge counter on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 1, Counter.CounterType.CHARGE));

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T), Remove a charge counter from Tendo Ice Bridge: Add one mana of
		// any color to your mana pool.
		this.addAbility(new TendoIceBridgeAbility2(state));
	}
}
