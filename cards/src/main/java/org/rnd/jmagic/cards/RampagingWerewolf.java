package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rampaging Werewolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RampagingWerewolf extends AlternateCard
{
	public RampagingWerewolf(GameState state)
	{
		super(state);
		this.setColorIndicator(Color.RED);

		this.setPower(6);
		this.setToughness(4);

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Rampaging Werewolf.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
