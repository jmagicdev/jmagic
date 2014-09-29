package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Witness of the Ages")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@ColorIdentity({})
public final class WitnessoftheAges extends Card
{
	public WitnessoftheAges(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Morph (5) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(5)"));
	}
}
