package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Faerie Invaders")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.ROGUE})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class FaerieInvaders extends Card
{
	public FaerieInvaders(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
