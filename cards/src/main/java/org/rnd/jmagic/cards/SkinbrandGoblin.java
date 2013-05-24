package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Skinbrand Goblin")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SkinbrandGoblin extends Card
{
	public SkinbrandGoblin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Bloodrush \u2014 (R), Discard Skinbrand Goblin: Target attacking
		// creature gets +2/+1 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Bloodrush(state, "(R)", "Skinbrand Goblin", +2, +1, "Target attacking creature gets +2/+1 until end of turn."));
	}
}
