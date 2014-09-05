package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Armored Warhorse")
@Types({Type.CREATURE})
@SubTypes({SubType.HORSE})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON)})
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
