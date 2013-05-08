package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Matron")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GoblinMatron extends Card
{
	public static final class GoblinMatronAbility0 extends EventTriggeredAbility
	{
		public GoblinMatronAbility0(GameState state)
		{
			super(state, "When Goblin Matron enters the battlefield, you may search your library for a Goblin card, reveal that card, and put it into your hand. If you do, shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Goblin card, reveal that card, and put it into your hand. Then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.GOBLIN)));
			this.addEffect(factory);
		}
	}

	public GoblinMatron(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Goblin Matron enters the battlefield, you may search your
		// library for a Goblin card, reveal that card, and put it into your
		// hand. If you do, shuffle your library.
		this.addAbility(new GoblinMatronAbility0(state));
	}
}
