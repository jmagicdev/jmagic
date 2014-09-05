package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Behemoth's Herald")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("G")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BehemothsHerald extends org.rnd.jmagic.cardTemplates.ShardsHerald
{
	public BehemothsHerald(GameState state)
	{
		super(state, Color.RED, Color.GREEN, Color.WHITE, "Godsire");

		this.setPower(1);
		this.setToughness(1);

		// (2)(G), (T), Sacrifice a red creature, a green creature, and a white
		// creature: Search your library for a card named Godsire and put it
		// onto the battlefield. Then shuffle your library.
	}
}
