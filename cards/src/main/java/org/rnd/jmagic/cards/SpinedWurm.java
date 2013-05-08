package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Spined Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STARTER_2000, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STRONGHOLD, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SpinedWurm extends Card
{
	public SpinedWurm(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);
	}
}
