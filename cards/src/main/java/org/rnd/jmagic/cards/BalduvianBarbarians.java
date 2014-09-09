package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Balduvian Barbarians")
@Types({Type.CREATURE})
@SubTypes({SubType.BARBARIAN, SubType.HUMAN})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class BalduvianBarbarians extends Card
{
	public BalduvianBarbarians(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);
	}
}
