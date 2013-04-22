package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Walking Corpse")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
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
