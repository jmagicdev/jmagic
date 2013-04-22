package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Natural Order")
@Types({Type.SORCERY})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.PORTAL, r = Rarity.RARE), @Printings.Printed(ex = Expansion.VISIONS, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class NaturalOrder extends Card
{
	public NaturalOrder(GameState state)
	{
		super(state);

		// As an additional cost to cast Natural Order, sacrifice a green
		// creature.
		SetGenerator greenCreatures = Intersect.instance(HasColor.instance(Color.GREEN), HasType.instance(Type.CREATURE));
		this.addCost(sacrifice(You.instance(), 1, greenCreatures, "sacrifice a green creature"));

		// Search your library for a green creature card and put it onto the
		// battlefield. Then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a green creature card and put it onto the battlefield. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(greenCreatures));
		this.addEffect(search);
	}
}
