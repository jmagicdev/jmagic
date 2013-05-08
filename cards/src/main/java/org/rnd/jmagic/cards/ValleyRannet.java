package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Valley Rannet")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4RG")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class ValleyRannet extends Card
{
	public ValleyRannet(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.MountainCycling(state, "(2)"));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.ForestCycling(state, "(2)"));
	}
}
