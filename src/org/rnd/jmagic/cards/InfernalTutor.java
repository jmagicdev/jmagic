package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Infernal Tutor")
@Types({Type.SORCERY})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class InfernalTutor extends Card
{
	public InfernalTutor(GameState state)
	{
		super(state);

		// Reveal a card from your hand.
		SetGenerator yourHand = HandOf.instance(You.instance());

		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal a card from your hand.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, InZone.instance(yourHand));

		// Search your library for a card with the same name as that card,
		// reveal it, put it into your hand, then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a card with the same name as that card, reveal it, put it into your hand, then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, yourHand);
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance(NameOf.instance(EffectResult.instance(reveal)))));

		// If you have no cards in hand, instead
		// Search your library for a card, put it into your hand, then shuffle
		// your library.
		EventFactory searchForAny = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a card, put it into your hand, then shuffle your library.");
		searchForAny.parameters.put(EventType.Parameter.CAUSE, This.instance());
		searchForAny.parameters.put(EventType.Parameter.PLAYER, You.instance());
		searchForAny.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		searchForAny.parameters.put(EventType.Parameter.TO, yourHand);

		EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Reveal a card from your hand. Search your library for a card with the same name as that card, reveal it, put it into your hand, then shuffle your library.\n\nIf you have no cards in hand, instead search your library for a card, put it into your hand, then shuffle your library.");
		effect.parameters.put(EventType.Parameter.IF, Hellbent.instance());
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(searchForAny));
		effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(sequence(reveal, search)));
		this.addEffect(effect);
	}
}
