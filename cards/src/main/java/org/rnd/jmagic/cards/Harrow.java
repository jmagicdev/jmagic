package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Harrow")
@Types({Type.INSTANT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class Harrow extends Card
{
	public Harrow(GameState state)
	{
		super(state);

		this.addCost(sacrifice(You.instance(), 1, LandPermanents.instance(), "sacrifice a land"));

		EventType.ParameterMap searchParameters = new EventType.ParameterMap();
		searchParameters.put(EventType.Parameter.CAUSE, This.instance());
		searchParameters.put(EventType.Parameter.CONTROLLER, You.instance());
		searchParameters.put(EventType.Parameter.PLAYER, You.instance());
		searchParameters.put(EventType.Parameter.NUMBER, Identity.instance(new org.rnd.util.NumberRange(0, 2)));
		searchParameters.put(EventType.Parameter.TO, Battlefield.instance());
		searchParameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters, "Search your library for up to two basic land cards and put them onto the battlefield. Then shuffle your library."));
	}
}
