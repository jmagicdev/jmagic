package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Moriok Reaver")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MoriokReaver extends Card
{
	public MoriokReaver(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
