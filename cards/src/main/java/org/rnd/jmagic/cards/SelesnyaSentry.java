package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Selesnya Sentry")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.ELEPHANT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SelesnyaSentry extends Card
{

	public SelesnyaSentry(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// (5)(G): Regenerate Selesnya Sentry.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(5)(G)", "Selesnya Sentry"));
	}
}
