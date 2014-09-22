package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wargate")
@Types({Type.SORCERY})
@ManaCost("XGWU")
@ColorIdentity({Color.WHITE, Color.BLUE, Color.GREEN})
public final class Wargate extends Card
{
	public Wargate(GameState state)
	{
		super(state);
		SetGenerator permanentCards = HasType.instance(Type.permanentTypes());
		SetGenerator costsXOrLess = HasConvertedManaCost.instance(Between.instance(numberGenerator(0), ValueOfX.instance(This.instance())));

		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a permanent card with converted mana cost X or less, put it onto the battlefield, then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(permanentCards, costsXOrLess)));
		this.addEffect(search);
	}
}
