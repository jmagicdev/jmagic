package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Shimmering Grotto")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class ShimmeringGrotto extends Card
{
	public static final class TapForColored extends org.rnd.jmagic.abilities.TapForMana
	{
		public TapForColored(GameState state)
		{
			super(state, "(WUBRG)");
			this.setName("(1), (T): Add one mana of any color to your mana pool.");
			this.setManaCost(new ManaPool("1"));
		}
	}

	public ShimmeringGrotto(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1), (T): Add one mana of any color to your mana pool.
		this.addAbility(new TapForColored(state));
	}
}
