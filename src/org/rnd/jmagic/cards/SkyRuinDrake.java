package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sky Ruin Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
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
