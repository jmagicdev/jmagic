package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Bog Raiders")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.COMMON), @Printings.Printed(ex = Portal.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BogRaiders extends Card
{
	public BogRaiders(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Swampwalk (This creature is unblockable as long as defending player
		// controls a Swamp.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
