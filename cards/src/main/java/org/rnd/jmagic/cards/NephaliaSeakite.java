package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nephalia Seakite")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class NephaliaSeakite extends Card
{
	public NephaliaSeakite(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
