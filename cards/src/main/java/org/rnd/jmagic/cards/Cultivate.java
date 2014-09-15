package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.numberGenerator;
import static org.rnd.jmagic.Convenience.putIntoHand;
import static org.rnd.jmagic.Convenience.shuffleLibrary;
import static org.rnd.jmagic.Convenience.simultaneous;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cultivate")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class Cultivate extends Card
{
	public Cultivate(GameState state)
	{
		super(state);

		// Search your library for up to two basic land cards, reveal those
		// cards,
		SetGenerator basicLands = Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND));

		EventFactory search = new EventFactory(EventType.SEARCH, "Search your library for up to two basic land cards, reveal those cards,");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.CARD, LibraryOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(basicLands));
		this.addEffect(search);
		SetGenerator found = EffectResult.instance(search);

		// and put one onto the battlefield tapped
		EventFactory drop = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "and put one onto the battlefield tapped");
		drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
		drop.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		drop.parameters.put(EventType.Parameter.OBJECT, found);
		drop.parameters.put(EventType.Parameter.EFFECT, Identity.instance(EventType.PUT_ONTO_BATTLEFIELD_TAPPED));

		// and the other into your hand.
		EventFactory take = putIntoHand(found, You.instance(), "and the other into your hand.");

		this.addEffect(simultaneous(drop, take));

		// Then shuffle your library,
		this.addEffect(shuffleLibrary(You.instance(), "Then shuffle your library."));
	}
}
