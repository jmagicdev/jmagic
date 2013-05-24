package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tormented Pariah")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR, SubType.WEREWOLF})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
@BackFace(RampagingWerewolf.class)
public final class TormentedPariah extends Card
{
	public TormentedPariah(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));

		this.setPower(3);
		this.setToughness(2);
	}
}
