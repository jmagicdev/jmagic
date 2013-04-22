package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plague Beetle")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PlagueBeetle extends Card
{
	public PlagueBeetle(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
