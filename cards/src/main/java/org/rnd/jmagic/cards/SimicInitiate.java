package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Simic Initiate")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MUTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class SimicInitiate extends Card
{
	public SimicInitiate(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Graft(state, 1));
	}
}
