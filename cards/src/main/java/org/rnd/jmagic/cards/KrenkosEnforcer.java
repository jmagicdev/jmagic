package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Krenko's Enforcer")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class KrenkosEnforcer extends Card
{
	public KrenkosEnforcer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Intimidate (This creature can't be blocked except by artifact
		// creatures and/or creatures that share a color with it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));
	}
}
