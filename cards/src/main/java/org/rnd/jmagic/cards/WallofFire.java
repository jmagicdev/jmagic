package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wall of Fire")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class WallofFire extends Card
{
	public WallofFire(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, this.getName()));
	}
}
