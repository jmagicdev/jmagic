package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glimpse the Unthinkable")
@Types({Type.SORCERY})
@ManaCost("UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class GlimpsetheUnthinkable extends Card
{
	public GlimpsetheUnthinkable(GameState state)
	{
		super(state);

		// Target player puts the top ten cards of his or her library into his
		// or her graveyard.
		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(millCards(targetedBy(target), 10, "Target player puts the top ten cards of his or her library into his or her graveyard."));
	}
}
