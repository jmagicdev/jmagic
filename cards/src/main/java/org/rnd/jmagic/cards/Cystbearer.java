package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Cystbearer")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Cystbearer extends Card
{
	public Cystbearer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
