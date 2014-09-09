package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Goblin Sky Raider")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class GoblinSkyRaider extends Card
{
	public GoblinSkyRaider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
