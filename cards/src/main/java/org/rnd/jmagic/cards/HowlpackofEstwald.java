package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Howlpack of Estwald")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class HowlpackofEstwald extends AlternateCard
{
	public HowlpackofEstwald(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		this.setColorIndicator(Color.GREEN);

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Howlpack of Estwald.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
