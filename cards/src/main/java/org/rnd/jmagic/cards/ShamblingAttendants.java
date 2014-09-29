package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Shambling Attendants")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("7B")
@ColorIdentity({Color.BLACK})
public final class ShamblingAttendants extends Card
{
	public ShamblingAttendants(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
