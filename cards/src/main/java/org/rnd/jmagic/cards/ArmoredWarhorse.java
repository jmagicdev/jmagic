package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Armored Warhorse")
@Types({Type.CREATURE})
@SubTypes({SubType.HORSE})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class ArmoredWarhorse extends Card
{
	public ArmoredWarhorse(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);
	}
}
