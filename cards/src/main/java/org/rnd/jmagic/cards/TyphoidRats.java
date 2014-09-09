package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Typhoid Rats")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class TyphoidRats extends Card
{
	public TyphoidRats(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
