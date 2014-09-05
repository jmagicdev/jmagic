package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mystical Tutor")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Mirage.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class MysticalTutor extends Card
{
	public MysticalTutor(GameState state)
	{
		super(state);

		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_ON_TOP, "Search your library for an instant or sorcery card and reveal that card. Shuffle your library, then put the card on top of it.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.INSTANT, Type.SORCERY)));
		this.addEffect(search);
	}
}
