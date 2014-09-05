package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Cloudfin Raptor")
@Types({Type.CREATURE})
@SubTypes({SubType.MUTANT, SubType.BIRD})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CloudfinRaptor extends Card
{
	public CloudfinRaptor(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Evolve (Whenever a creature enters the battlefield under your
		// control, if that creature has greater power or toughness than this
		// creature, put a +1/+1 counter on this creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evolve(state));
	}
}
