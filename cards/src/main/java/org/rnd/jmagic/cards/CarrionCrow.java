package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Carrion Crow")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.BIRD})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class CarrionCrow extends Card
{
	public CarrionCrow(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying (This creature can't be blocked except by creatures with
		// flying or reach.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Carrion Crow enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
	}
}
