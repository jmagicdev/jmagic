package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Salt Road Patrol")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class SaltRoadPatrol extends Card
{
	public SaltRoadPatrol(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Outlast (1)(W) ((1)(W), (T): Put a +1/+1 counter on this creature.
		// Outlast only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Outlast(state, "(1)(W)"));
	}
}
