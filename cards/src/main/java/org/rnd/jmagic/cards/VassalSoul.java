package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vassal Soul")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1(W/U)(W/U)")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class VassalSoul extends Card
{
	public VassalSoul(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
