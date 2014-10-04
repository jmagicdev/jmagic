package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Cloaked Siren")
@Types({Type.CREATURE})
@SubTypes({SubType.SIREN})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class CloakedSiren extends Card
{
	public CloakedSiren(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
