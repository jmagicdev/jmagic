package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rotting Fensnake")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.SNAKE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class RottingFensnake extends Card
{
	public RottingFensnake(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(1);
	}
}
