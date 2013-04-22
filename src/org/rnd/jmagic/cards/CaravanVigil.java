package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Caravan Vigil")
@Types({Type.SORCERY})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CaravanVigil extends Card
{
	public CaravanVigil(GameState state)
	{
		super(state);

		// Search your library for a basic land card, reveal it, put it into
		// your hand, then shuffle your library.

		// Morbid \u2014 You may put that card onto the battlefield instead of
		// putting it into your hand if a creature died this turn.

		SetGenerator library = LibraryOf.instance(You.instance());
		SetGenerator basic = HasSuperType.instance(SuperType.BASIC);
		SetGenerator lands = HasType.instance(Type.LAND);

		EventFactory search = new EventFactory(EventType.SEARCH, "Search your library for a basic land card and reveal it.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.CARD, library);
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(basic, lands)));

		EventFactory putIntoHand = new EventFactory(EventType.MOVE_OBJECTS, "Put that card into your hand.");
		putIntoHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putIntoHand.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		putIntoHand.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(search));

		EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put that card onto the battlefield.");
		putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(search));

		EventFactory shuffle = shuffleYourLibrary("Shuffle your library.");

		EventFactory youMayPutOntoBattlefield = youMay(putOntoBattlefield, "You may put that card onto the battlefield.");
		EventFactory ifPutOntoBattlefield = ifElse(youMayPutOntoBattlefield, putIntoHand, "You may put that card onto the battlefield, otherwise, put it into your hand.");
		EventFactory ifMorbid = sequence(search, ifPutOntoBattlefield, shuffle);
		EventFactory ifNotMorbid = sequence(search, putIntoHand, shuffle);
		this.addEffect(ifThenElse(Morbid.instance(), ifMorbid, ifNotMorbid, "Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.\n\nYou may put that card onto the battlefield instead of putting it into your hand if a creature died this turn."));

		state.ensureTracker(new Morbid.Tracker());
	}
}
