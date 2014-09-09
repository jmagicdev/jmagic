package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ironfang")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@ColorIdentity({Color.RED})
public final class Ironfang extends AlternateCard
{
	public Ironfang(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.setColorIndicator(Color.RED);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Ironfang.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
