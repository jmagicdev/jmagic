package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wasteland Viper")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class WastelandViper extends Card
{
	public WastelandViper(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Bloodrush \u2014 (G), Discard Wasteland Viper: Target attacking
		// creature gets +1/+2 and gains deathtouch until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Bloodrush(state, "(G)", "Wasteland Viper", +1, +2, "Target attacking creature gets +1/+2 and gains deathtouch until end of turn.", org.rnd.jmagic.abilities.keywords.Deathtouch.class));
	}
}
