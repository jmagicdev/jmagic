package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Birds of Paradise")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class BirdsofParadise extends Card
{
	public BirdsofParadise(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
