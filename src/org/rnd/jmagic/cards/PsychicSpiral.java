package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Psychic Spiral")
@Types({Type.INSTANT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class PsychicSpiral extends Card
{
	public PsychicSpiral(GameState state)
	{
		super(state);

		// Shuffle all cards from your graveyard into your library.
		SetGenerator cardsInYard = InZone.instance(GraveyardOf.instance(You.instance()));

		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle all cards from your graveyard into your library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(You.instance(), cardsInYard));
		this.addEffect(shuffle);

		// Target player puts that many cards from the top of his or her library
		// into his or her graveyard.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator thoseCards = RelativeComplement.instance(EffectResult.instance(shuffle), LibraryOf.instance(You.instance()));
		SetGenerator thatMany = Count.instance(thoseCards);
		this.addEffect(millCards(target, thatMany, "Target player puts that many cards from the top of his or her library into his or her graveyard."));
	}
}
