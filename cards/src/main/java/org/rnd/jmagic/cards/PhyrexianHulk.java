package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Phyrexian Hulk")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.UNCOMMON)})
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
