package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Myr Enforcer")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR})
@ManaCost("7")
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
