package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Talon Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class TalonSliver extends Card
{
	public TalonSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// All Sliver creatures have first strike.
		this.addAbility(new org.rnd.jmagic.abilities.AllSliverCreaturesHave(state, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "All Sliver creatures have first strike."));
	}
}
