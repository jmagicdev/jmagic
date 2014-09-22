package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bloodfire Expert")
@Types({Type.CREATURE})
@SubTypes({SubType.EFREET, SubType.MONK})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class BloodfireExpert extends Card
{
	public BloodfireExpert(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));
	}
}
