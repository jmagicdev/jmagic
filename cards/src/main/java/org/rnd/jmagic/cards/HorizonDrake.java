package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Horizon Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class HorizonDrake extends Card
{
	public HorizonDrake(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Flying, protection from lands
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, LandPermanents.instance(), "lands"));
	}
}
