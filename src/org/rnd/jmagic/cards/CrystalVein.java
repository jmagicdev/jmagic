package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Crystal Vein")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CrystalVein extends Card
{
	public static final class CrystalVeinAbility1 extends ActivatedAbility
	{
		public CrystalVeinAbility1(GameState state)
		{
			super(state, "(T), Sacrifice Crystal Vein: Add (2) to your mana pool.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Crystal Vein"));
			this.addEffect(addManaToYourManaPoolFromAbility("(2)"));
		}
	}

	public CrystalVein(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T), Sacrifice Crystal Vein: Add (2) to your mana pool.
		this.addAbility(new CrystalVeinAbility1(state));
	}
}
