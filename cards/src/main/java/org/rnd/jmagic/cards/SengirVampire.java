package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;

@Name("Sengir Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class SengirVampire extends Card
{
	public SengirVampire(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new VampireKill(state, "Sengir Vampire"));
	}
}
