package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archangel's Light")
@Types({Type.SORCERY})
@ManaCost("7W")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class ArchangelsLight extends Card
{
	public ArchangelsLight(GameState state)
	{
		super(state);

		// You gain 2 life for each card in your graveyard,
		SetGenerator count = Count.instance(InZone.instance(GraveyardOf.instance(You.instance())));
		this.addEffect(gainLife(You.instance(), count, "You gain 2 life for each card in your graveyard,"));

		// then shuffle your graveyard into your library.
		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "then shuffle your graveyard into your library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(InZone.instance(GraveyardOf.instance(You.instance())), You.instance()));
		this.addEffect(shuffle);
	}
}
