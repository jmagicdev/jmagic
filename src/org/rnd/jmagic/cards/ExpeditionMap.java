package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Expedition Map")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({})
public final class ExpeditionMap extends Card
{
	public static final class OneUseMap extends ActivatedAbility
	{
		public OneUseMap(GameState state)
		{
			super(state, "(2), (T), Sacrifice Expedition Map: Search your library for a land card, reveal it, and put it into your hand. Then shuffle your library.");

			this.setManaCost(new ManaPool("2"));

			this.costsTap = true;

			this.addCost(sacrificeThis("Expedition Map"));

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a land card, reveal it, and put it into your hand. Then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.LAND)));

			this.addEffect(factory);
		}
	}

	public ExpeditionMap(GameState state)
	{
		super(state);

		// (2), (T), Sacrifice Expedition Map: Search your library for a land
		// card, reveal it, and put it into your hand. Then shuffle your
		// library.
		this.addAbility(new OneUseMap(state));
	}
}
