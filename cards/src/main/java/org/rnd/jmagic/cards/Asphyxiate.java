package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Asphyxiate")
@Types({Type.SORCERY})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class Asphyxiate extends Card
{
	public Asphyxiate(GameState state)
	{
		super(state);

		// Destroy target untapped creature.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Untapped.instance(), CreaturePermanents.instance()), "target untapped creature"));
		this.addEffect(destroy(target, "Destroy target untapped creature."));
	}
}
