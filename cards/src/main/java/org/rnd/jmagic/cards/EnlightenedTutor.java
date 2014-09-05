package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Enlightened Tutor")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Mirage.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class EnlightenedTutor extends Card
{
	public EnlightenedTutor(GameState state)
	{
		super(state);

		// Search your library for an artifact or enchantment card and reveal
		// that card. Shuffle your library, then put the card on top of it.

		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_ON_TOP, "Search your library for an artifact or enchantment card and reveal that card. Shuffle your library, then put the card on top of it.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.ARTIFACT, Type.ENCHANTMENT)));
		this.addEffect(search);
	}
}
