package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Giant Scorpion")
@Types({Type.CREATURE})
@SubTypes({SubType.SCORPION})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GiantScorpion extends Card
{
	public GiantScorpion(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
