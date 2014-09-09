package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Giant Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class GiantSpider extends Card
{
	public GiantSpider(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
