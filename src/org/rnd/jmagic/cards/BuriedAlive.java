package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Buried Alive")
@Types({Type.SORCERY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BuriedAlive extends Card
{
	public BuriedAlive(GameState state)
	{
		super(state);

		// Search your library for up to three creature cards and put them into
		// your graveyard. Then shuffle your library.

		EventFactory searchAndPut = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to three creature cards and put them into your graveyard.");
		searchAndPut.parameters.put(EventType.Parameter.CAUSE, This.instance());
		searchAndPut.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		searchAndPut.parameters.put(EventType.Parameter.PLAYER, You.instance());
		searchAndPut.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 3));
		searchAndPut.parameters.put(EventType.Parameter.TO, GraveyardOf.instance(You.instance()));
		searchAndPut.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance())));
		this.addEffect(searchAndPut);

		this.addEffect(shuffleYourLibrary("Then shuffle your library."));
	}
}
