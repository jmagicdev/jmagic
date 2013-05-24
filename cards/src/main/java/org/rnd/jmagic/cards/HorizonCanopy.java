package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Horizon Canopy")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class HorizonCanopy extends Card
{
	public static final class HorizonCanopyAbility0 extends ActivatedAbility
	{
		public HorizonCanopyAbility0(GameState state)
		{
			super(state, "(T), Pay 1 life: Add (G) or (W) to your mana pool.");
			this.costsTap = true;
			this.addCost(payLife(You.instance(), 1, "Pay 1 life"));
			this.addEffect(addManaToYourManaPoolFromAbility("(GW)"));
		}
	}

	public static final class HorizonCanopyAbility1 extends ActivatedAbility
	{
		public HorizonCanopyAbility1(GameState state)
		{
			super(state, "(1), (T), Sacrifice Horizon Canopy: Draw a card.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Horizon Canopy"));
			this.addEffect(drawACard());
		}
	}

	public HorizonCanopy(GameState state)
	{
		super(state);

		// (T), Pay 1 life: Add (G) or (W) to your mana pool.
		this.addAbility(new HorizonCanopyAbility0(state));

		// (1), (T), Sacrifice Horizon Canopy: Draw a card.
		this.addAbility(new HorizonCanopyAbility1(state));
	}
}
