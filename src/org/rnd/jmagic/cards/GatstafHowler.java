package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gatstaf Howler")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class GatstafHowler extends AlternateCard
{
	public GatstafHowler(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.setColorIndicator(Color.GREEN);

		// Intimidate (This creature can't be blocked except by artifact
		// creatures and/or creatures that share a color with it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Gatstaf Howler.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
