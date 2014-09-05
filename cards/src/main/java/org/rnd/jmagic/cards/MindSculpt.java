package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mind Sculpt")
@Types({Type.SORCERY})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MindSculpt extends Card
{
	public MindSculpt(GameState state)
	{
		super(state);

		// Target opponent puts the top seven cards of his or her library into
		// his or her graveyard.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		this.addEffect(millCards(target, 7, "Target opponent puts the top seven cards of his or her library into his or her graveyard."));
	}
}
