package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Raging Goblin")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.BERSERKER})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Exodus.class, r = Rarity.COMMON), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.COMMON), @Printings.Printed(ex = Portal.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RagingGoblin extends Card
{
	public RagingGoblin(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
