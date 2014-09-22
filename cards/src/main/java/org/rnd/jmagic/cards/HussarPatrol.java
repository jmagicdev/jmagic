package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hussar Patrol")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("2WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class HussarPatrol extends Card
{
	public HussarPatrol(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
