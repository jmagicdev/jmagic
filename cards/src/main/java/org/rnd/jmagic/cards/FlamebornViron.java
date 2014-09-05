package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Flameborn Viron")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FlamebornViron extends Card
{
	public FlamebornViron(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);
	}
}
