package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Skyline Predator")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SkylinePredator extends Card
{
	public SkylinePredator(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
