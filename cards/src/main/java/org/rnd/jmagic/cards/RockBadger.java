package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rock Badger")
@Types({Type.CREATURE})
@SubTypes({SubType.BADGER, SubType.BEAST})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class RockBadger extends Card
{
	public RockBadger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Mountainwalk(state));
	}
}
