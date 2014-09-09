package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rushwood Dryad")
@Types({Type.CREATURE})
@SubTypes({SubType.DRYAD})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class RushwoodDryad extends Card
{
	public RushwoodDryad(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Forestwalk(state));
	}
}
