package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mystical Teachings")
@Types({Type.INSTANT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class MysticalTeachings extends Card
{
	public MysticalTeachings(GameState state)
	{
		super(state);

		// Search your library for an instant card or a card with flash, reveal
		// it, and put it into your hand. Then shuffle your library.
		SetGenerator flashyThings = Union.instance(HasType.instance(Type.INSTANT), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flash.class));
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an instant card or a card with flash, reveal it, and put it into your hand. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(flashyThings));
		this.addEffect(search);

		// Flashback (5)(B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(5)(B)"));
	}
}
