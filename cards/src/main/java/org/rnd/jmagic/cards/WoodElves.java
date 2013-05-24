package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wood Elves")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.ELF})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EXODUS, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class WoodElves extends Card
{
	public static final class IBringTheWoodsWithMe extends EventTriggeredAbility
	{
		public IBringTheWoodsWithMe(GameState state)
		{
			super(state, "When Wood Elves enters the battlefield, search your library for a Forest card and put that card onto the battlefield. Then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Forest card and put that card onto the battlefield. Then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.FOREST)));
			this.addEffect(factory);
		}
	}

	public WoodElves(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Wood Elves enters the battlefield, search your library for a
		// Forest card and put that card onto the battlefield. Then shuffle your
		// library.
		this.addAbility(new IBringTheWoodsWithMe(state));
	}
}
