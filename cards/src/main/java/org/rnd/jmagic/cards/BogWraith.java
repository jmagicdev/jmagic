package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bog Wraith")
@Types({Type.CREATURE})
@SubTypes({SubType.WRAITH})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class BogWraith extends Card
{
	public BogWraith(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
