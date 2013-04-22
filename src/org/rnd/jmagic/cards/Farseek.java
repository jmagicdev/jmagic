package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Farseek")
@Types({Type.SORCERY})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Farseek extends Card
{
	public Farseek(GameState state)
	{
		super(state);

		// Search your library for a Plains, Island, Swamp, or Mountain card and
		// put it onto the battlefield tapped. Then shuffle your library.
		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Plains, Island, Swamp, or Mountain card and put it onto the battlefield tapped. Then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		factory.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
		factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(Union.instance(HasSubType.instance(SubType.PLAINS), HasSubType.instance(SubType.ISLAND), HasSubType.instance(SubType.SWAMP), HasSubType.instance(SubType.MOUNTAIN))));
		this.addEffect(factory);
	}
}
