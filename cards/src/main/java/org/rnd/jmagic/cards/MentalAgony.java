package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mental Agony")
@Types({Type.SORCERY})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class MentalAgony extends Card
{
	public MentalAgony(GameState state)
	{
		super(state);

		// Target player discards two cards and loses 2 life.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(discardCards(target, 2, "Target player discards two cards"));
		this.addEffect(loseLife(target, 2, "and loses 2 life."));
	}
}
