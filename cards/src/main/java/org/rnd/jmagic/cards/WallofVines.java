package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Wall of Vines")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT, SubType.WALL})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WallofVines extends Card
{
	public WallofVines(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
