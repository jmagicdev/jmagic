package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Priests of Norn")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class PriestsofNorn extends Card
{
	public PriestsofNorn(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
