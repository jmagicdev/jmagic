package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Diabolic Revelation")
@Types({Type.SORCERY})
@ManaCost("X3BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DiabolicRevelation extends Card
{
	public DiabolicRevelation(GameState state)
	{
		super(state);

		// Search your library for up to X cards and put those cards into your
		// hand. Then shuffle your library.
		SetGenerator X = ValueOfX.instance(This.instance());
		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to X cards and put those cards into your hand. Then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, X);
		factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		this.addEffect(factory);
	}
}
