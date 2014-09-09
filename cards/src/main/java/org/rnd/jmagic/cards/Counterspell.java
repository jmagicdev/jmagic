package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Counterspell")
@Types({Type.INSTANT})
@ManaCost("UU")
@ColorIdentity({Color.BLUE})
public final class Counterspell extends Card
{
	public Counterspell(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");

		this.addEffect(counter(targetedBy(target), "Counter target spell."));
	}
}
