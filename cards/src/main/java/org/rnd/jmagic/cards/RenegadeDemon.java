package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Renegade Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class RenegadeDemon extends Card
{
	public RenegadeDemon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);
	}
}
