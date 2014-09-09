package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Goliath Sphinx")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("5UU")
@ColorIdentity({Color.BLUE})
public final class GoliathSphinx extends Card
{
	public GoliathSphinx(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(7);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
