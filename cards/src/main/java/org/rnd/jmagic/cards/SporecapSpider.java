package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sporecap Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class SporecapSpider extends Card
{
	public SporecapSpider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
