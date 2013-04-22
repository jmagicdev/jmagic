package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Merciless Predator")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class MercilessPredator extends AlternateCard
{
	public MercilessPredator(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.setColorIndicator(Color.RED);

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Merciless Predator.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
