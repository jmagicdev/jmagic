package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Beseech the Queen")
@Types({Type.SORCERY})
@ManaCost("(2/B)(2/B)(2/B)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BeseechtheQueen extends Card
{
	public BeseechtheQueen(GameState state)
	{
		super(state);

		// Search your library for a card with converted mana cost less than or
		// equal to the number of lands you control, reveal it, and put it into
		// your hand. Then shuffle your library.
		SetGenerator landsYouControl = Intersect.instance(ControlledBy.instance(You.instance()), LandPermanents.instance());
		SetGenerator validSearches = HasConvertedManaCost.instance(Between.instance(numberGenerator(0), Count.instance(landsYouControl)));

		EventType.ParameterMap searchParameters = new EventType.ParameterMap();
		searchParameters.put(EventType.Parameter.CAUSE, This.instance());
		searchParameters.put(EventType.Parameter.PLAYER, You.instance());
		searchParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		searchParameters.put(EventType.Parameter.TYPE, Identity.instance(validSearches));
		searchParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters, "Search your library for a card with converted mana cost less than or equal to the number of lands you control, reveal it, and put it into your hand. Then shuffle your library."));
	}
}
