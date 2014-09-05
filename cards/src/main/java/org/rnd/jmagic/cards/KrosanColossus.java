package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Krosan Colossus")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("6GGG")
@Printings({@Printings.Printed(ex = Onslaught.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class KrosanColossus extends Card
{
	public KrosanColossus(GameState state)
	{
		super(state);

		this.setPower(9);
		this.setToughness(9);

		// Morph (6)(G)(G)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(6)(G)(G)"));
	}
}
