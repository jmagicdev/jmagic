package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Fabricate")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Fabricate extends Card
{
	public Fabricate(GameState state)
	{
		super(state);

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.ARTIFACT)));
		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, parameters, "Search your library for an artifact card, reveal it, and put it into your hand. Then shuffle your library."));
	}
}
