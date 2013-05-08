package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vorstclaw")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR, SubType.ELEMENTAL})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Vorstclaw extends Card
{
	public Vorstclaw(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);
	}
}
