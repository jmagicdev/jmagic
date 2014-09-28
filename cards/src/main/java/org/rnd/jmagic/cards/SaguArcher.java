package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sagu Archer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHER, SubType.NAGA})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class SaguArcher extends Card
{
	public SaguArcher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// Morph (4)(G) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(4)(G)"));
	}
}
