package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dutiful Thrull")
@Types({Type.CREATURE})
@SubTypes({SubType.THRULL})
@ManaCost("W")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class DutifulThrull extends Card
{
	public DutifulThrull(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (B): Regenerate Dutiful Thrull.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)", this.getName()));
	}
}
