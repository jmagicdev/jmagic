package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Scoria Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4R")
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
