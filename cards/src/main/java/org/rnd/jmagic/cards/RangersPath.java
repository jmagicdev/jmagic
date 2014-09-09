package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ranger's Path")
@Types({Type.SORCERY})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class RangersPath extends Card
{
	public RangersPath(GameState state)
	{
		super(state);

		// Search your library for up to two Forest cards and put them onto the
		// battlefield tapped. Then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to two Forest cards and put them onto the battlefield tapped. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 2));
		search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		search.parameters.put(EventType.Parameter.TAPPED, NonEmpty.instance());
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.FOREST)));
		this.addEffect(search);
	}
}
