package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Makindi Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class MakindiGriffin extends Card
{
	public MakindiGriffin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
