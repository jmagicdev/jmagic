package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Blood Ogre")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.OGRE})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BloodOgre extends Card
{
	public BloodOgre(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Bloodthirst 1 (If an opponent was dealt damage this turn, this
		// creature enters the battlefield with a +1/+1 counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bloodthirst.Final(state, 1));

		// First strike (This creature deals combat damage before creatures
		// without first strike.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
