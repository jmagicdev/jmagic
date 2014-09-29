package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Snowhorn Rider")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("3GUR")
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class SnowhornRider extends Card
{
	public SnowhornRider(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Morph (2)(G)(U)(R) (You may cast this card face down as a 2/2
		// creature for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)(G)(U)(R)"));
	}
}
