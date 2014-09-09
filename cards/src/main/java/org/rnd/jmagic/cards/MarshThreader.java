package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Marsh Threader")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.KOR})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class MarshThreader extends Card
{
	public MarshThreader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Swampwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));
	}
}
