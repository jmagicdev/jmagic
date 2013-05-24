package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Reckless Waif")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE, SubType.WEREWOLF})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
@BackFace(MercilessPredator.class)
public final class RecklessWaif extends Card
{
	public RecklessWaif(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Reckless Waif.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
