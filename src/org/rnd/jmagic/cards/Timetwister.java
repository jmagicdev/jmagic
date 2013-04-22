package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Timetwister")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Timetwister extends Card
{
	public Timetwister(GameState state)
	{
		super(state);

		// Each player shuffles his or her hand and graveyard into his or her
		// library,
		SetGenerator handAndGraveyard = Union.instance(InZone.instance(HandOf.instance(Players.instance())), InZone.instance(GraveyardOf.instance(Players.instance())));

		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Each player shuffles his or her hand and graveyard into his or her library,");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(handAndGraveyard, Players.instance()));
		this.addEffect(shuffle);

		// then draws seven cards.
		this.addEffect(drawCards(Players.instance(), 7, "then draws seven cards."));
	}
}
