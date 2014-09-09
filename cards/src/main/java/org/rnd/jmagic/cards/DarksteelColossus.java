package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Darksteel Colossus")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("(11)")
@ColorIdentity({})
public final class DarksteelColossus extends Card
{
	public DarksteelColossus(GameState state)
	{
		super(state);

		this.setPower(11);
		this.setToughness(11);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		this.addAbility(new org.rnd.jmagic.abilities.ColossusShuffle(state, this.getName()));
	}
}
