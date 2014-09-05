package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Afflicted Deserter")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
@BackFace(WerewolfRansacker.class)
public final class AfflictedDeserter extends Card
{
	public AfflictedDeserter(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Afflicted Deserter.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
