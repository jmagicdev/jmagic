package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Canopy Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.COMMON)})
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
