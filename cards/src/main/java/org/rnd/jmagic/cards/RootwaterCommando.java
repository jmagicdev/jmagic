package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rootwater Commando")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class RootwaterCommando extends Card
{
	public RootwaterCommando(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));
	}
}
