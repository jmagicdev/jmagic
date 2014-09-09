package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Canopy Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class CanopySpider extends Card
{
	public CanopySpider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
