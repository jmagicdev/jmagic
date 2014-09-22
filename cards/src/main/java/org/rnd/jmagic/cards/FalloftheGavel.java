package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fall of the Gavel")
@Types({Type.INSTANT})
@ManaCost("3WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class FalloftheGavel extends Card
{
	public FalloftheGavel(GameState state)
	{
		super(state);

		// Counter target spell. You gain 5 life.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		this.addEffect(counter(target, "Counter target spell."));
		this.addEffect(gainLife(You.instance(), 5, "You gain 5 life."));
	}
}
