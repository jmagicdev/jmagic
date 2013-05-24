package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stroke of Genius")
@Types({Type.INSTANT})
@ManaCost("X2U")
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class StrokeofGenius extends Card
{
	public StrokeofGenius(GameState state)
	{
		super(state);

		// Target player draws X cards.
		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(drawCards(targetedBy(target), ValueOfX.instance(This.instance()), "Target player draws X cards."));
	}
}
