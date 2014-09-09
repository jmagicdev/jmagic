package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Blackcleave Goblin")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.GOBLIN})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class BlackcleaveGoblin extends Card
{
	public BlackcleaveGoblin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Infect
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
