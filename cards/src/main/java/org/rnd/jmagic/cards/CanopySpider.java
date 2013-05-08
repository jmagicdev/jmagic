package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Canopy Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CanopySpider extends Card
{
	public CanopySpider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
