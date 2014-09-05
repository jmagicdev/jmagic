package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Hexplate Golem")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("7")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class HexplateGolem extends Card
{
	public HexplateGolem(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(7);
	}
}
