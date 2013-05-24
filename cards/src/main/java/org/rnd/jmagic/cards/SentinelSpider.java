package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sentinel Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SentinelSpider extends Card
{
	public SentinelSpider(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Vigilance (Attacking doesn't cause this creature to tap.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
