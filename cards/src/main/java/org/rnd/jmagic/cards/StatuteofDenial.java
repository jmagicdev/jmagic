package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Statute of Denial")
@Types({Type.INSTANT})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class StatuteofDenial extends Card
{
	public StatuteofDenial(GameState state)
	{
		super(state);

		// Counter target spell.
		Target target = this.addTarget(Spells.instance(), "target spell");
		this.addEffect(counter(targetedBy(target), "Counter target spell."));

		// If you control a blue creature, draw a card, then discard a card.
		SetGenerator blueCreature = Intersect.instance(HasColor.instance(Color.BLUE), HasType.instance(Type.CREATURE));
		SetGenerator youControlBlueCreature = Intersect.instance(ControlledBy.instance(You.instance()), blueCreature);
		EventFactory drawDiscard = sequence(drawCards(You.instance(), 1, "Draw a card,"), discardCards(You.instance(), 1, ", then discard a card."));
		this.addEffect(ifThen(youControlBlueCreature, drawDiscard, "If you control a blue creature, draw a card, then discard a card."));
	}
}
