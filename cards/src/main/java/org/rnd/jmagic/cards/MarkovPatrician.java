package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Markov Patrician")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class MarkovPatrician extends Card
{
	public MarkovPatrician(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
