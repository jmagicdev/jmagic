package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vindicate")
@Types({Type.SORCERY})
@ManaCost("1WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class Vindicate extends Card
{
	public Vindicate(GameState state)
	{
		super(state);

		// Destroy target permanent.
		SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

		this.addEffect(destroy(target, "Destroy target permanent."));
	}
}
