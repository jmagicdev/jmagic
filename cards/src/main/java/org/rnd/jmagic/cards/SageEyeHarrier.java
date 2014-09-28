package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sage-Eye Harrier")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.WARRIOR})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class SageEyeHarrier extends Card
{
	public SageEyeHarrier(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Morph (3)(W) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(3)(W)"));
	}
}
