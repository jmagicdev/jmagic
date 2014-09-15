package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Excoriate")
@Types({Type.SORCERY})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class Excoriate extends Card
{
	public Excoriate(GameState state)
	{
		super(state);

		// Exile target tapped creature.
		SetGenerator tappedThings = Intersect.instance(Tapped.instance(), HasType.instance(Type.CREATURE));
		SetGenerator target = targetedBy(this.addTarget(tappedThings, "target tapped creature"));
		this.addEffect(exile(target, "Exile target tapped creature."));
	}
}
