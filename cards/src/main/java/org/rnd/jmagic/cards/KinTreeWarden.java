package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kin-Tree Warden")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class KinTreeWarden extends Card
{
	public KinTreeWarden(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (2): Regenerate Kin-Tree Warden.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(2)", this.getName()));

		// Morph (G) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(G)"));
	}
}
