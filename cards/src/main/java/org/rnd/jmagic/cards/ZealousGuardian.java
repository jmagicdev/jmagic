package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Zealous Guardian")
@Types({Type.CREATURE})
@SubTypes({SubType.KITHKIN, SubType.SOLDIER})
@ManaCost("(WU)")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class ZealousGuardian extends Card
{
	public ZealousGuardian(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));
	}
}
