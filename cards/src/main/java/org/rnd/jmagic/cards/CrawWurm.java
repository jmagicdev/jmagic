package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Craw Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class CrawWurm extends Card
{
	public CrawWurm(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);
	}
}
