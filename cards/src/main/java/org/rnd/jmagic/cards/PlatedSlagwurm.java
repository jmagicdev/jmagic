package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Plated Slagwurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4GGG")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class PlatedSlagwurm extends Card
{
	public PlatedSlagwurm(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
	}
}
