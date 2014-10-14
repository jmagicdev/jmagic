package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Triton Shorestalker")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.MERFOLK})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class TritonShorestalker extends Card
{
	public TritonShorestalker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Triton Shorestalker can't be blocked.
		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, this.getName()));
	}
}
