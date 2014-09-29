package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Woolly Loxodon")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT, SubType.WARRIOR})
@ManaCost("5GG")
@ColorIdentity({Color.GREEN})
public final class WoollyLoxodon extends Card
{
	public WoollyLoxodon(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(7);

		// Morph (5)(G) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(5)(G)"));
	}
}
