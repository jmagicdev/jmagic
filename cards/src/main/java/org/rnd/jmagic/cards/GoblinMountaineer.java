package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Goblin Mountaineer")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SCOUT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class GoblinMountaineer extends Card
{
	public GoblinMountaineer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Mountainwalk(state));
	}
}
