package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Whitesun's Passage")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class WhitesunsPassage extends Card
{
	public WhitesunsPassage(GameState state)
	{
		super(state);

		// You gain 5 life.
		this.addEffect(gainLife(You.instance(), 5, "You gain 5 life."));
	}
}
