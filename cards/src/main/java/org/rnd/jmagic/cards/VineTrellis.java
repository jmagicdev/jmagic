package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vine Trellis")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL, SubType.PLANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class VineTrellis extends Card
{
	public VineTrellis(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
