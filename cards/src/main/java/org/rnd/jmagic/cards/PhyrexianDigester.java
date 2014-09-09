package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Phyrexian Digester")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("3")
@ColorIdentity({})
public final class PhyrexianDigester extends Card
{
	public PhyrexianDigester(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
