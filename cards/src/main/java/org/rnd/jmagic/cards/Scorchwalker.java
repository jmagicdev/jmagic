package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Scorchwalker")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Scorchwalker extends Card
{
	public Scorchwalker(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(1);

		// Bloodrush \u2014 (1)(R)(R), Discard Scorchwalker: Target attacking
		// creature gets +5/+1 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Bloodrush(state, "(1)(R)(R)", "Scorchwalker", +5, +1, "Target attacking creature gets +5/+1 until end of turn."));
	}
}
