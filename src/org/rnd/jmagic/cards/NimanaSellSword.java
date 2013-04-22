package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nimana Sell-Sword")
@Types({Type.CREATURE})
@SubTypes({SubType.ALLY, SubType.WARRIOR, SubType.HUMAN})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class NimanaSellSword extends Card
{
	public NimanaSellSword(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.ZendikarAllyCounter(state, this.getName()));
	}
}
