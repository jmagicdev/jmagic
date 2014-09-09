package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Village Ironsmith")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("1R")
@ColorIdentity({Color.RED})
@BackFace(Ironfang.class)
public final class VillageIronsmith extends Card
{
	public VillageIronsmith(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Village Ironsmith.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
