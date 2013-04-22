package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dragon's Herald")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SHAMAN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class DragonsHerald extends org.rnd.jmagic.cardTemplates.ShardsHerald
{
	public DragonsHerald(GameState state)
	{
		super(state, Color.BLACK, Color.RED, Color.GREEN, "Hellkite Overlord");

		this.setPower(1);
		this.setToughness(1);

		// (2)(R), (T), Sacrifice a black creature, a red creature, and a green
		// creature: Search your library for a card named Hellkite Overlord and
		// put it onto the battlefield. Then shuffle your library.
	}
}
