package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Lightning Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class LightningElemental extends Card
{
	public LightningElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
