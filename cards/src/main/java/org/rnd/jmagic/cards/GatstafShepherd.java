package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gatstaf Shepherd")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
@BackFace(GatstafHowler.class)
public final class GatstafShepherd extends Card
{
	public GatstafShepherd(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Gatstaf Shepherd.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
