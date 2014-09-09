package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Windrider Eel")
@Types({Type.CREATURE})
@SubTypes({SubType.FISH})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class WindriderEel extends Card
{
	public WindriderEel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, Windrider Eel gets +2/+2 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.LandfallForPump(state, this.getName(), +2, +2));
	}
}
