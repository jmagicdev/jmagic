package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dusk Imp")
@Types({Type.CREATURE})
@SubTypes({SubType.IMP})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DuskImp extends Card
{
	public DuskImp(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
