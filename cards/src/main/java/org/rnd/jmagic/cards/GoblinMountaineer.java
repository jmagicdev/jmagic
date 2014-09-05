package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Goblin Mountaineer")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SCOUT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.COMMON), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinMountaineer extends Card
{
	public GoblinMountaineer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Mountainwalk(state));
	}
}
