package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Air Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class AirElemental extends Card
{
	public AirElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
