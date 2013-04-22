package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Boundless Realms")
@Types({Type.SORCERY})
@ManaCost("6G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class BoundlessRealms extends Card
{
	public BoundlessRealms(GameState state)
	{
		super(state);

		SetGenerator lands = HasType.instance(Type.LAND);
		SetGenerator youControl = ControlledBy.instance(You.instance());
		SetGenerator landsYouControl = Intersect.instance(lands, youControl);

		// Search your library for up to X basic land cards, where X is the
		// number of lands you control, and put them onto the battlefield
		// tapped. Then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to X basic land cards, where X is the number of lands you control, and put them onto the battlefield tapped. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, Between.instance(numberGenerator(0), Count.instance(landsYouControl)));
		search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		search.parameters.put(EventType.Parameter.TAPPED, NonEmpty.instance());
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(lands));
		this.addEffect(search);
	}
}
