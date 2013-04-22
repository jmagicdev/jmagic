package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Barbarian General")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER, SubType.BARBARIAN})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.PORTAL_THREE_KINGDOMS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class BarbarianGeneral extends Card
{
	public BarbarianGeneral(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Horsemanship(state));
	}
}
