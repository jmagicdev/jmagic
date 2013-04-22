package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Krallenhorde Wantons")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class KrallenhordeWantons extends AlternateCard
{
	public KrallenhordeWantons(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		this.setColorIndicator(Color.GREEN);

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Krallenhorde Wantons.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
