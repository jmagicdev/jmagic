package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Guardian Lions")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GuardianLions extends Card
{
	public GuardianLions(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(6);

		// Vigilance (Attacking doesn't cause this creature to tap.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
