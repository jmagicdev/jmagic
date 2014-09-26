package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Jeskai Windscout")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.SCOUT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class JeskaiWindscout extends Card
{
	public JeskaiWindscout(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));
	}
}
