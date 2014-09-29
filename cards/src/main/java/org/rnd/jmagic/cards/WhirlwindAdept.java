package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Whirlwind Adept")
@Types({Type.CREATURE})
@SubTypes({SubType.DJINN, SubType.MONK})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class WhirlwindAdept extends Card
{
	public WhirlwindAdept(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));
	}
}
