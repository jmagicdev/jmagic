package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Cliff Threader")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SCOUT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class CliffThreader extends Card
{
	public CliffThreader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Mountainwalk(state));
	}
}
