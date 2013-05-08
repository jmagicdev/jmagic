package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sidewinder Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SidewinderSliver extends Card
{
	public SidewinderSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// All Sliver creatures have flanking.
		this.addAbility(new org.rnd.jmagic.abilities.AllSliverCreaturesHave(state, org.rnd.jmagic.abilities.keywords.Flanking.class, "All Sliver creatures have flanking."));
	}
}
