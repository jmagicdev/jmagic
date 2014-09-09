package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Phantom Warrior")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION, SubType.WARRIOR})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class PhantomWarrior extends Card
{
	public PhantomWarrior(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, "Phantom Warrior"));
	}
}
