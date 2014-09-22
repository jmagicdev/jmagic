package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Canyon Lurkers")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class CanyonLurkers extends Card
{
	public CanyonLurkers(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(2);

		// Morph (3)(R) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(3)(R)"));
	}
}
