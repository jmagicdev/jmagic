package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Rally the Peasants")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class RallythePeasants extends Card
{
	public RallythePeasants(GameState state)
	{
		super(state);

		// Creatures you control get +2/+0 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +2, +0, "Creatures you control get +2/+0 until end of turn."));

		// Flashback (2)(R) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(2)(R)"));
	}
}
