package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Scarwood Treefolk")
@Types({Type.CREATURE})
@SubTypes({SubType.TREEFOLK})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ScarwoodTreefolk extends Card
{
	public ScarwoodTreefolk(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
	}
}
