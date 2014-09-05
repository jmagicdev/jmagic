package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Talon Trooper")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.SCOUT})
@ManaCost("1WU")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class TalonTrooper extends Card
{
	public TalonTrooper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
