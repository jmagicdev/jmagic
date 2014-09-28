package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Monastery Flock")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class MonasteryFlock extends Card
{
	public MonasteryFlock(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		// Defender, flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Morph (U) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(U)"));
	}
}
