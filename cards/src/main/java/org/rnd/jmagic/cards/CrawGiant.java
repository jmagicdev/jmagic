package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Craw Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("3GGGG")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class CrawGiant extends Card
{
	public CrawGiant(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Rampage(state, 2));
	}
}
