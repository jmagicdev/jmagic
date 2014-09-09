package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Perish")
@Types({Type.SORCERY})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class Perish extends Card
{
	public Perish(GameState state)
	{
		super(state);

		// Destroy all green creatures. They can't be regenerated.
		this.addEffects(bury(this, Intersect.instance(HasColor.instance(Color.GREEN), CreaturePermanents.instance()), "Destroy all green creatures. They can't be regenerated."));
	}
}
