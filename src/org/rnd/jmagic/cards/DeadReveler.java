package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dead Reveler")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DeadReveler extends Card
{
	public DeadReveler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));
	}
}
