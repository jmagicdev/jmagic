package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Highborn Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("BB")
@ColorIdentity({Color.BLACK})
public final class HighbornGhoul extends Card
{
	public HighbornGhoul(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Intimidate (This creature can't be blocked except by artifact
		// creatures and/or creatures that share a color with it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));
	}
}
