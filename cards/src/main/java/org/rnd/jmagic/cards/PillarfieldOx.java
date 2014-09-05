package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Pillarfield Ox")
@Types({Type.CREATURE})
@SubTypes({SubType.OX})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class PillarfieldOx extends Card
{
	public PillarfieldOx(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);
	}
}
