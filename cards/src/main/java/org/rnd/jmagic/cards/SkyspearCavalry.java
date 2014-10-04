package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Skyspear Cavalry")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class SkyspearCavalry extends Card
{
	public SkyspearCavalry(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Double strike (This creature deals both first-strike and regular
		// combat damage.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));
	}
}
