package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Desert Twister")
@Types({Type.SORCERY})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class DesertTwister extends Card
{
	public DesertTwister(GameState state)
	{
		super(state);

		// Destroy target permanent.
		SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
		this.addEffect(destroy(target, "Destroy target permanent."));
	}
}
