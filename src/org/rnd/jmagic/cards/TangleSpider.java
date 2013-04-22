package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tangle Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TangleSpider extends Card
{
	public TangleSpider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
	}
}
