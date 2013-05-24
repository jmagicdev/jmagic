package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Phyrexian Hulk")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PhyrexianHulk extends Card
{
	public PhyrexianHulk(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);
	}
}
