package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gluttonous Slime")
@Types({Type.CREATURE})
@SubTypes({SubType.OOZE})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class GluttonousSlime extends Card
{
	public GluttonousSlime(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Devour 1
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Devour(state, 1));
	}
}
