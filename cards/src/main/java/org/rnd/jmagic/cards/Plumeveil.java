package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plumeveil")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("(W/U)(W/U)(W/U)")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class Plumeveil extends Card
{
	public Plumeveil(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Defender, flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
