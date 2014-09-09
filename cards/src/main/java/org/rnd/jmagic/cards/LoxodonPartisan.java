package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Loxodon Partisan")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.ELEPHANT})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class LoxodonPartisan extends Card
{
	public LoxodonPartisan(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Battle cry (Whenever this creature attacks, each other attacking
		// creature gets +1/+0 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.BattleCry(state));
	}
}
