package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Loxodon Convert")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.ELEPHANT})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class LoxodonConvert extends Card
{
	public LoxodonConvert(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);
	}
}
