package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Plague Beetle")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class PlagueBeetle extends Card
{
	public PlagueBeetle(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
