package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Wrecking Ogre")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.WARRIOR})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class WreckingOgre extends Card
{
	public WreckingOgre(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Double strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));

		// Bloodrush \u2014 (3)(R)(R), Discard Wrecking Ogre: Target attacking
		// creature gets +3/+3 and gains double strike until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Bloodrush(state, "(3)(R)(R)", "Wrecking Ogre", +3, +3, "Target attacking creature gets +3/+3 and gains double strike until end of turn.", org.rnd.jmagic.abilities.keywords.DoubleStrike.class));
	}
}
