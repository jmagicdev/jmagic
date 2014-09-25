package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Force Away")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class ForceAway extends Card
{
	public ForceAway(GameState state)
	{
		super(state);

		// Return target creature to its owner's hand.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(bounce(target, "Return target creature to its owner's hand."));

		// Ferocious \u2014 If you control a creature with power 4 or greater,
		// you may draw a card. If you do, discard a card.
		EventFactory discard = discardCards(You.instance(), 1, "Discard a card");
		EventFactory drawDiscard = ifThen(youMay(drawACard()), discard, "You may draw a card. If you do, discard a card.");
		this.addEffect(ifThen(Ferocious.instance(), drawDiscard, "If you control a creature with power 4 or greater, you may draw a card. If you do, discard a card."));
	}
}
