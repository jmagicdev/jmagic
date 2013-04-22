package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kuldotha Ringleader")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.GIANT})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class KuldothaRingleader extends Card
{
	public KuldothaRingleader(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Battle cry (Whenever this creature attacks, each other attacking
		// creature gets +1/+0 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.BattleCry(state));

		// Kuldotha Ringleader attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));
	}
}
