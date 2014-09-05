package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Sky Ruin Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SkyRuinDrake extends Card
{
	public SkyRuinDrake(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
