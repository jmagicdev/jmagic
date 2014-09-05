package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Prickly Boggart")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.GOBLIN})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Morningtide.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PricklyBoggart extends Card
{
	public PricklyBoggart(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fear(state));
	}
}
