package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Scoria Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ScoriaElemental extends Card
{
	public ScoriaElemental(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(1);
	}
}
