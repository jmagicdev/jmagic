package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Duskhunter Bat")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DuskhunterBat extends Card
{
	public DuskhunterBat(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Bloodthirst 1 (If an opponent was dealt damage this turn, this
		// creature enters the battlefield with a +1/+1 counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bloodthirst.Final(state, 1));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
