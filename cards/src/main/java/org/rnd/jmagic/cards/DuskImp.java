package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dusk Imp")
@Types({Type.CREATURE})
@SubTypes({SubType.IMP})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class DuskImp extends Card
{
	public DuskImp(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
