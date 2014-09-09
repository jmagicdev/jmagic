package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Spineless Thug")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.MERCENARY})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class SpinelessThug extends Card
{
	public SpinelessThug(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));
	}
}
