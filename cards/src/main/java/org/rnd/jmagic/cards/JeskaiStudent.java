package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Jeskai Student")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class JeskaiStudent extends Card
{
	public JeskaiStudent(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));
	}
}
