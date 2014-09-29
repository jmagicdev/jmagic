package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("War Behemoth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5W")
@ColorIdentity({Color.WHITE})
public final class WarBehemoth extends Card
{
	public WarBehemoth(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(6);

		// Morph (4)(W) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(4)(W)"));
	}
}
