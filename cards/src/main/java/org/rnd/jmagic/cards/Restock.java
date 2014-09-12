package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Restock")
@Types({Type.SORCERY})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class Restock extends Card
{
	public Restock(GameState state)
	{
		super(state);

		// Return two target cards from your graveyard to your hand.
		Target twoCards = this.addTarget(InZone.instance(GraveyardOf.instance(You.instance())), "two target cards from your graveyard");
		twoCards.setNumber(2, 2);
		SetGenerator target = targetedBy(twoCards);
		this.addEffect(putIntoHand(target, You.instance(), "Return two target cards from your graveyard to your hand."));

		// Exile Restock.
		this.addEffect(exile(This.instance(), "Exile Restock."));
	}
}
