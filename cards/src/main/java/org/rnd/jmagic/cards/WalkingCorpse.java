package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Walking Corpse")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class WalkingCorpse extends Card
{
	public WalkingCorpse(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
