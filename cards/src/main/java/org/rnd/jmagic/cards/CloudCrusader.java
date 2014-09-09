package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Cloud Crusader")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class CloudCrusader extends Card
{
	public CloudCrusader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// First strike (This creature deals combat damage before creatures
		// without first strike.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
