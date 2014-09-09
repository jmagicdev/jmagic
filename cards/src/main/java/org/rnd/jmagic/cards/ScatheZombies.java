package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Scathe Zombies")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class ScatheZombies extends Card
{
	public ScatheZombies(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
	}
}
