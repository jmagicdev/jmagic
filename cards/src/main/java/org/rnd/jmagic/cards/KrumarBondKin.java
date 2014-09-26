package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Krumar Bond-Kin")
@Types({Type.CREATURE})
@SubTypes({SubType.ORC, SubType.WARRIOR})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class KrumarBondKin extends Card
{
	public KrumarBondKin(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Morph (4)(B) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(4)(B)"));
	}
}
