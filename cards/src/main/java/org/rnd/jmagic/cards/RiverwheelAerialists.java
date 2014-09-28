package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Riverwheel Aerialists")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.DJINN})
@ManaCost("5U")
@ColorIdentity({Color.BLUE})
public final class RiverwheelAerialists extends Card
{
	public RiverwheelAerialists(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));
	}
}
