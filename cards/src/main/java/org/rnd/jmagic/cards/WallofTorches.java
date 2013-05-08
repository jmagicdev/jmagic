package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wall of Torches")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class WallofTorches extends Card
{
	public WallofTorches(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
	}
}
