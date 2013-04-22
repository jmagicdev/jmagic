package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Spawn of Rix Maadi")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("3BR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class SpawnofRixMaadi extends Card
{
	public SpawnofRixMaadi(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));
	}
}
