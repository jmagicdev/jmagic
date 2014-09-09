package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mind Rot")
@Types({Type.SORCERY})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class MindRot extends Card
{
	public MindRot(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(discardCards(targetedBy(target), 2, "Target player discards two cards."));
	}
}
