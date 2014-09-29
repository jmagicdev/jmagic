package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Venerable Lammasu")
@Types({Type.CREATURE})
@SubTypes({SubType.LAMMASU})
@ManaCost("6W")
@ColorIdentity({Color.WHITE})
public final class VenerableLammasu extends Card
{
	public VenerableLammasu(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
