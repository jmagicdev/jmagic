package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Countermand")
@Types({Type.INSTANT})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class Countermand extends Card
{
	public Countermand(GameState state)
	{
		super(state);

		// Counter target spell.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		this.addEffect(counter(target, "Counter target spell."));

		// Its controller puts the top four cards of his or her library into his
		// or her graveyard.
		this.addEffect(millCards(ControllerOf.instance(target), 4, "Its controller puts the top four cards of his or her library into his or her graveyard."));
	}
}
