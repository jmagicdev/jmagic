package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Daggerdrome Imp")
@Types({Type.CREATURE})
@SubTypes({SubType.IMP})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class DaggerdromeImp extends Card
{
	public DaggerdromeImp(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
