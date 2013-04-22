package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Demon's Herald")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DemonsHerald extends org.rnd.jmagic.cardTemplates.ShardsHerald
{
	public DemonsHerald(GameState state)
	{
		super(state, Color.BLUE, Color.BLACK, Color.RED, "Prince of Thralls");

		this.setPower(1);
		this.setToughness(1);

		// (2)(B), (T), Sacrifice a blue creature, a black creature, and a red
		// creature: Search your library for a card named Prince of Thralls and
		// put it onto the battlefield. Then shuffle your library.
	}
}
