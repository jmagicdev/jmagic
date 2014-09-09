package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Darksteel Sentinel")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@ColorIdentity({})
public final class DarksteelSentinel extends Card
{
	public DarksteelSentinel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));
	}
}
