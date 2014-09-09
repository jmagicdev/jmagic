package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plague Myr")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("2")
@ColorIdentity({})
public final class PlagueMyr extends Card
{
	public PlagueMyr(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
	}
}
