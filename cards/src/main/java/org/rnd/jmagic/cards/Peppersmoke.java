package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Peppersmoke")
@Types({Type.INSTANT, Type.TRIBAL})
@SubTypes({SubType.FAERIE})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Peppersmoke extends Card
{
	public Peppersmoke(GameState state)
	{
		super(state);

		// Target creature gets -1/-1 until end of turn. If you control a
		// Faerie, draw a card.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), -1, -1, "Target creature gets -1/-1 until end of turn."));

		SetGenerator youControlAFaerie = Intersect.instance(HasSubType.instance(SubType.FAERIE), ControlledBy.instance(You.instance()));

		EventFactory conditionalDraw = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If you control a Faerie, draw a card.");
		conditionalDraw.parameters.put(EventType.Parameter.IF, youControlAFaerie);
		conditionalDraw.parameters.put(EventType.Parameter.THEN, Identity.instance(drawCards(You.instance(), 1, "draw a card")));
		this.addEffect(conditionalDraw);
	}
}
