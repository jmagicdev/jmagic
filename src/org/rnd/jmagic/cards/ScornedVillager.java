package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Scorned Villager")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
@BackFace(MoonscarredWerewolf.class)
public final class ScornedVillager extends Card
{
	public ScornedVillager(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Scorned Villager.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
