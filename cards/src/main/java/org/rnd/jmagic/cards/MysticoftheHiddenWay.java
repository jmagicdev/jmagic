package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mystic of the Hidden Way")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.HUMAN})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class MysticoftheHiddenWay extends Card
{
	public MysticoftheHiddenWay(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Mystic of the Hidden Way can't be blocked.
		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, this.getName()));

		// Morph (2)(U) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)(U)"));
	}
}
