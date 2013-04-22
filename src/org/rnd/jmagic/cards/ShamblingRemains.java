package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Shambling Remains")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR, SubType.ZOMBIE})
@ManaCost("1BR")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class ShamblingRemains extends Card
{
	public ShamblingRemains(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Shambling Remains can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));

		// Unearth (B)(R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(B)(R)"));
	}
}
