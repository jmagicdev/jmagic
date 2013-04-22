package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hagra Crocodile")
@Types({Type.CREATURE})
@SubTypes({SubType.CROCODILE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class HagraCrocodile extends Card
{
	public HagraCrocodile(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Hagra Crocodile can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, Hagra Crocodile gets +2/+2 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.LandfallForPump(state, this.getName(), +2, +2));
	}
}
