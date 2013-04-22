package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Severed Legion")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SeveredLegion extends Card
{
	public SeveredLegion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fear(state));
	}
}
