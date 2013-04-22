package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lambholt Elder")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
@BackFace(SilverpeltWerewolf.class)
public final class LambholtElder extends Card
{
	public LambholtElder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Lambholt Elder.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
