package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hollowhenge Beast")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class HollowhengeBeast extends Card
{
	public HollowhengeBeast(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);
	}
}
