package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lurking Crocodile")
@Types({Type.CREATURE})
@SubTypes({SubType.CROCODILE})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class LurkingCrocodile extends Card
{
	public LurkingCrocodile(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Bloodthirst 1 (If an opponent was dealt damage this turn, this
		// creature enters the battlefield with a +1/+1 counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bloodthirst.Final(state, 1));

		// Islandwalk (This creature is unblockable as long as defending player
		// controls an Island.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));
	}
}
