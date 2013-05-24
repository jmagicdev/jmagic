package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Time Reversal")
@Types({Type.SORCERY})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class TimeReversal extends Card
{
	public TimeReversal(GameState state)
	{
		super(state);

		// Each player shuffles his or her hand and graveyard into his or her
		// library,
		SetGenerator handsAndGraveyards = InZone.instance(Union.instance(HandOf.instance(Players.instance()), GraveyardOf.instance(Players.instance())));

		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Each player shuffles his or her hand and graveyard into his or her library,");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(handsAndGraveyards, Players.instance()));
		this.addEffect(shuffle);

		// then draws seven cards.
		this.addEffect(drawCards(Players.instance(), 7, "then draws seven cards."));

		// Exile Time Reversal.
		this.addEffect(exile(This.instance(), "Exile Time Reversal."));
	}
}
