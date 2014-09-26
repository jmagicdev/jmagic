package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Far Wanderings")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class FarWanderings extends Card
{
	public FarWanderings(GameState state)
	{
		super(state);

		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a basic land card and put that card onto the battlefield tapped. Then shuffle your library.\n\nThreshold \u2014 If seven or more cards are in your graveyard, instead search your library for three basic land cards and put them onto the battlefield tapped. Then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, IfThenElse.instance(Threshold.instance(), numberGenerator(3), numberGenerator(1)));
		factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		factory.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
		factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
		this.addEffect(factory);
	}
}
