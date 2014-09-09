package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Looming Shade")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class LoomingShade extends Card
{
	public LoomingShade(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.ShadePump(state, "Looming Shade"));
	}
}
