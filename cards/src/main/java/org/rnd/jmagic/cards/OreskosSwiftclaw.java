package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Oreskos Swiftclaw")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.WARRIOR})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class OreskosSwiftclaw extends Card
{
	public OreskosSwiftclaw(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);
	}
}
