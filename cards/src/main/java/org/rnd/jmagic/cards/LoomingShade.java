package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Looming Shade")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class LoomingShade extends Card
{
	public LoomingShade(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.ShadePump(state, "Looming Shade"));
	}
}
