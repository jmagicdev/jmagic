package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gem of Becoming")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class GemofBecoming extends Card
{
	public static final class GemofBecomingAbility0 extends ActivatedAbility
	{
		public GemofBecomingAbility0(GameState state)
		{
			super(state, "(3), (T), Sacrifice Gem of Becoming: Search your library for an Island card, a Swamp card, and a Mountain card. Reveal those cards and put them into your hand. Then shuffle your library.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Gem of Becoming"));

			EventFactory factoryIsland = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an Island card, ");
			factoryIsland.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factoryIsland.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factoryIsland.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factoryIsland.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factoryIsland.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			factoryIsland.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factoryIsland.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.ISLAND)));
			this.addEffect(factoryIsland);

			EventFactory factorySwamp = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "a Swamp card, ");
			factorySwamp.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factorySwamp.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factorySwamp.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factorySwamp.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factorySwamp.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			factorySwamp.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factorySwamp.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.SWAMP)));
			this.addEffect(factorySwamp);

			EventFactory factoryMountain = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "and a Mountain card. Reveal those cards and put them into your hand. Then shuffle your library.");
			factoryMountain.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factoryMountain.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factoryMountain.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factoryMountain.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factoryMountain.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			factoryMountain.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factoryMountain.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.MOUNTAIN)));
			this.addEffect(factoryMountain);

		}
	}

	public GemofBecoming(GameState state)
	{
		super(state);

		// (3), (T), Sacrifice Gem of Becoming: Search your library for an
		// Island card, a Swamp card, and a Mountain card. Reveal those cards
		// and put them into your hand. Then shuffle your library.
		this.addAbility(new GemofBecomingAbility0(state));
	}
}
