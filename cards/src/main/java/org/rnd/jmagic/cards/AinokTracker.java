package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ainok Tracker")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.HOUND})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class AinokTracker extends Card
{
	public AinokTracker(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Morph (4)(R) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(4)(R)"));
	}
}
