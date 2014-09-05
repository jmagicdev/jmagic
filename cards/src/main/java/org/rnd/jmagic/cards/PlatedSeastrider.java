package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Plated Seastrider")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class PlatedSeastrider extends Card
{
	public PlatedSeastrider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);
	}
}
