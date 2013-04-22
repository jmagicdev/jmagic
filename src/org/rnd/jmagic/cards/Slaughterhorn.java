package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Slaughterhorn")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Slaughterhorn extends Card
{
	public Slaughterhorn(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Bloodrush \u2014 (G), Discard Slaughterhorn: Target attacking
		// creature gets +3/+2 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Bloodrush(state, "(G)", "Slaughterhorn", +3, +2, "Target attacking creature gets +3/+2 until end of turn."));
	}
}
