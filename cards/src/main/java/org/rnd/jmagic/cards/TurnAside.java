package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Turn Aside")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class TurnAside extends Card
{
	public TurnAside(GameState state)
	{
		super(state);

		// Counter target spell that targets a permanent you control.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Spells.instance(), HasTarget.instance(ControlledBy.instance(You.instance()))), "target spell that targets a permanent you control"));
		this.addEffect(counter(target, "Counter target spell that targets a permanent you control."));
	}
}
