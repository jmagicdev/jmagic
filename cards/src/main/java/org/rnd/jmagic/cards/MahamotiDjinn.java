package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mahamoti Djinn")
@Types({Type.CREATURE})
@SubTypes({SubType.DJINN})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class MahamotiDjinn extends Card
{
	public MahamotiDjinn(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
