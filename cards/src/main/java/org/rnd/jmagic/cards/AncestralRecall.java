package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ancestral Recall")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class AncestralRecall extends Card
{
	public AncestralRecall(GameState state)
	{
		super(state);

		// Target player draws three cards.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(drawCards(target, 3, "Target player draws three cards."));
	}
}
