package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rotting Legion")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class RottingLegion extends Card
{
	public RottingLegion(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Rotting Legion enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
	}
}
