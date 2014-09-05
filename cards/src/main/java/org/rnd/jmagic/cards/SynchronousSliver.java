package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Synchronous Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = PlanarChaos.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SynchronousSliver extends Card
{
	public SynchronousSliver(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// All Sliver creatures have vigilance.
		this.addAbility(new org.rnd.jmagic.abilities.AllSliverCreaturesHave(state, org.rnd.jmagic.abilities.keywords.Vigilance.class, "All Sliver creatures have vigilance."));
	}
}
