package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cancel")
@Types({Type.INSTANT})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class Cancel extends Card
{
	public Cancel(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");

		this.addEffect(counter(targetedBy(target), "Counter target spell."));
	}
}
