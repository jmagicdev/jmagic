package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bloodbraid Elf")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.ELF})
@ManaCost("2RG")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class BloodbraidElf extends Card
{
	public BloodbraidElf(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Cascade
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cascade(state));
	}
}
