package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trinket Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class TrinketMage extends Card
{
	public static final class GetCogs extends EventTriggeredAbility
	{
		public GetCogs(GameState state)
		{
			super(state, "When Trinket Mage enters the battlefield, you may search your library for an artifact card with converted mana cost 1 or less, reveal that card, and put it into your hand. If you do, shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator cogs = Intersect.instance(HasType.instance(Type.ARTIFACT), HasConvertedManaCost.instance(Between.instance(null, 1)));

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an artifact card with converted mana cost 1 or less, reveal that card, and put it into your hand; then shuffle your library");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(cogs));
			this.addEffect(youMay(search, "You may search your library for an artifact card with converted mana cost 1 or less, reveal that card, and put it into your hand. If you do, shuffle your library."));
		}
	}

	public TrinketMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Trinket Mage enters the battlefield, you may search your library
		// for an artifact card with converted mana cost 1 or less, reveal that
		// card, and put it into your hand. If you do, shuffle your library.
		this.addAbility(new GetCogs(state));
	}
}
