package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Silvercoat Lion")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SilvercoatLion extends Card
{
	public SilvercoatLion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
