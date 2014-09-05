package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Stonework Puma")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CAT, SubType.ALLY})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class StoneworkPuma extends Card
{
	public StoneworkPuma(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
