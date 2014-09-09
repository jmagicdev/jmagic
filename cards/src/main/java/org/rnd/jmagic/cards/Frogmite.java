package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Frogmite")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.FROG})
@ManaCost("4")
@ColorIdentity({})
public final class Frogmite extends Card
{
	public Frogmite(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Affinity for artifacts
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Affinity.ForArtifacts(state));
	}
}
