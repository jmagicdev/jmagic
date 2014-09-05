package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Rusted Sentinel")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RustedSentinel extends Card
{
	public RustedSentinel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Rusted Sentinel enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
	}
}
