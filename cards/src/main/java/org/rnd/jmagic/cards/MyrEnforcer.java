package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Myr Enforcer")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR})
@ManaCost("7")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class MyrEnforcer extends Card
{
	public MyrEnforcer(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Affinity for artifacts
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Affinity.ForArtifacts(state));
	}
}
