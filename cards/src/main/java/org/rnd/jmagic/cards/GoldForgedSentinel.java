package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gold-Forged Sentinel")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CHIMERA})
@ManaCost("6")
@ColorIdentity({})
public final class GoldForgedSentinel extends Card
{
	public GoldForgedSentinel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
