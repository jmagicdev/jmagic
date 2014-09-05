package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Phyrexian Walker")
@Types({Type.ARTIFACT, Type.CREATURE})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Visions.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class PhyrexianWalker extends Card
{
	public PhyrexianWalker(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);
	}
}
