package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Search Warrant")
@Types({Type.SORCERY})
@ManaCost("WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class SearchWarrant extends Card
{
	public SearchWarrant(GameState state)
	{
		super(state);

		// Target player reveals his or her hand. You gain life equal to the
		// number of cards in that player's hand.
		SetGenerator hand = HandOf.instance(targetedBy(this.addTarget(Players.instance(), "target player")));
		this.addEffect(reveal(hand, "Target player reveals his or her hand."));
		this.addEffect(gainLife(You.instance(), Count.instance(InZone.instance(hand)), "You gain life equal to the number of cards in that player's hand."));
	}
}
