package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Moonscarred Werewolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class MoonscarredWerewolf extends AlternateCard
{
	public MoonscarredWerewolf(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.setColorIndicator(Color.GREEN);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// (T): Add (G)(G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(G)(G)"));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Moonscarred Werewolf.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
