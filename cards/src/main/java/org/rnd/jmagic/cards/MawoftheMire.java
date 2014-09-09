package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Maw of the Mire")
@Types({Type.SORCERY})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class MawoftheMire extends Card
{
	public MawoftheMire(GameState state)
	{
		super(state);

		// Destroy target land. You gain 4 life.
		SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
		this.addEffect(destroy(target, "Destroy target land."));
		this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
	}
}
