package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wild Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class WildGriffin extends Card
{
	public WildGriffin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
