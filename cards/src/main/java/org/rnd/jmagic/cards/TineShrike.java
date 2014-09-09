package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tine Shrike")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class TineShrike extends Card
{
	public TineShrike(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
