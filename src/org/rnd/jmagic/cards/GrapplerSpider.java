package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Grappler Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GrapplerSpider extends Card
{
	public GrapplerSpider(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
