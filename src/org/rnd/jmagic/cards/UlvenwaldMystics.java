package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ulvenwald Mystics")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN, SubType.WEREWOLF})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
@BackFace(UlvenwaldPrimordials.class)
public final class UlvenwaldMystics extends Card
{
	public UlvenwaldMystics(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Ulvenwald Mystics.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
