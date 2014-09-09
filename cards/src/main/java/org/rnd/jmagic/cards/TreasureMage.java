package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Treasure Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class TreasureMage extends Card
{
	public static final class TreasureMageAbility0 extends EventTriggeredAbility
	{
		public TreasureMageAbility0(GameState state)
		{
			super(state, "When Treasure Mage enters the battlefield, you may search your library for an artifact card with converted mana cost 6 or greater, reveal that card, and put it into your hand. If you do, shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator cogs = Intersect.instance(HasType.instance(Type.ARTIFACT), HasConvertedManaCost.instance(Between.instance(6, null)));

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an artifact card with converted mana cost 6 or greater, reveal that card, and put it into your hand; then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(cogs));
			this.addEffect(youMay(search, "You may search your library for an artifact card with converted mana cost 6 or greater, reveal that card, and put it into your hand. If you do, shuffle your library."));
		}
	}

	public TreasureMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Treasure Mage enters the battlefield, you may search your
		// library for an artifact card with converted mana cost 6 or greater,
		// reveal that card, and put it into your hand. If you do, shuffle your
		// library.
		this.addAbility(new TreasureMageAbility0(state));
	}
}
