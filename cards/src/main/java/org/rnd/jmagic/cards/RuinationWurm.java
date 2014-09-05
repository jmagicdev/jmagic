package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ruination Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4RG")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class RuinationWurm extends Card
{
	public RuinationWurm(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(6);
	}
}
