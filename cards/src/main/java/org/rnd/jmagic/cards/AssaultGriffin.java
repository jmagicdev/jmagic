package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Assault Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class AssaultGriffin extends Card
{
	public AssaultGriffin(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
