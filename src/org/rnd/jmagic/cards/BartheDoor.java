package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Bar the Door")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BartheDoor extends Card
{
	public BartheDoor(GameState state)
	{
		super(state);

		// Creatures you control get +0/+4 until end of turn.
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +0, +4, "Creatures you control get +0/+4 until end of turn."));
	}
}
