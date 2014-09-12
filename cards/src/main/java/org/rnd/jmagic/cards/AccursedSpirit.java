package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Accursed Spirit")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class AccursedSpirit extends Card
{
	public AccursedSpirit(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Intimidate (This creature can't be blocked except by artifact
		// creatures and/or creatures that share a color with it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));
	}
}
