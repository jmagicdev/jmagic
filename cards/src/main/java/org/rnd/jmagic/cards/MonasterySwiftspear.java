package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Monastery Swiftspear")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.HUMAN})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class MonasterySwiftspear extends Card
{
	public MonasterySwiftspear(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));
	}
}
