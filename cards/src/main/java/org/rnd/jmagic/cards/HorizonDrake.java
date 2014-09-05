package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Horizon Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.UNCOMMON)})
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
