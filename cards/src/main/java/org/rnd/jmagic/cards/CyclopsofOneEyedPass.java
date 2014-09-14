package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Cyclops of One-Eyed Pass")
@Types({Type.CREATURE})
@SubTypes({SubType.CYCLOPS})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class CyclopsofOneEyedPass extends Card
{
	public CyclopsofOneEyedPass(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(2);
	}
}
