package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Into the North")
@Types({Type.SORCERY})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Coldsnap.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class IntotheNorth extends Card
{
	public IntotheNorth(GameState state)
	{
		super(state);

		// Search your library for a snow land card and put it onto the
		// battlefield tapped. Then shuffle your library.
		EventFactory effect = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a snow land card and put that card onto the battlefield tapped. Then shuffle your library.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
		effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		effect.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
		effect.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		effect.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.SNOW), HasType.instance(Type.LAND))));
		this.addEffect(effect);
	}
}
