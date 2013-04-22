package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Glistener Elf")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.WARRIOR})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GlistenerElf extends Card
{
	public GlistenerElf(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
