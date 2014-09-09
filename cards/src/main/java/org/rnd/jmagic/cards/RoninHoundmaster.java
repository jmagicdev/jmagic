package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ronin Houndmaster")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SAMURAI})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class RoninHoundmaster extends Card
{
	public RoninHoundmaster(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bushido(state, 1));
	}
}
