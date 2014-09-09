package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ashcoat Bear")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAR})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class AshcoatBear extends Card
{
	public AshcoatBear(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));
	}
}
