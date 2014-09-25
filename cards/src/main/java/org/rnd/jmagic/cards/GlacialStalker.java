package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Glacial Stalker")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("5U")
@ColorIdentity({Color.BLUE})
public final class GlacialStalker extends Card
{
	public GlacialStalker(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Morph (4)(U) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(4)(U)"));
	}
}
