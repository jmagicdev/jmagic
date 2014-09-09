package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Raging Goblin")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.BERSERKER})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class RagingGoblin extends Card
{
	public RagingGoblin(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
