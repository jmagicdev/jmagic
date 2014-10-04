package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Pharika's Chosen")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class PharikasChosen extends Card
{
	public PharikasChosen(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
