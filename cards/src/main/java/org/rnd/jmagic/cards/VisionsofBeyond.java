package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Visions of Beyond")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class VisionsofBeyond extends Card
{
	public VisionsofBeyond(GameState state)
	{
		super(state);

		// Draw a card. If a graveyard has twenty or more cards in it, draw
		// three cards instead.
		SetGenerator maxInYard = Count.instance(LargestSet.instance(SplitOnOwner.instance(InZone.instance(GraveyardOf.instance(Players.instance())))));
		SetGenerator twentyOrMore = Intersect.instance(maxInYard, Between.instance(20, null));
		SetGenerator number = IfThenElse.instance(twentyOrMore, numberGenerator(3), numberGenerator(1));
		this.addEffect(drawCards(You.instance(), number, "Draw a card. If a graveyard has twenty or more cards in it, draw three cards instead."));
	}
}
