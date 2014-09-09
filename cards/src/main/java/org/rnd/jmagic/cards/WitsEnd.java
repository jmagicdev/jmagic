package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wit's End")
@Types({Type.SORCERY})
@ManaCost("5BB")
@ColorIdentity({Color.BLACK})
public final class WitsEnd extends Card
{
	public WitsEnd(GameState state)
	{
		super(state);

		// Target player discards his or her hand.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(discardHand(target, "Target player discards his or her hand."));
	}
}
