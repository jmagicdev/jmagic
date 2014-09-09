package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Furnace Whelp")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class FurnaceWhelp extends Card
{
	public FurnaceWhelp(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, this.getName()));
	}
}
