package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mysteries of the Deep")
@Types({Type.INSTANT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MysteriesoftheDeep extends Card
{
	public MysteriesoftheDeep(GameState state)
	{
		super(state);

		// Draw two cards.

		// Landfall \u2014 If you had a land enter the battlefield under your
		// control this turn, draw three cards instead.
		state.ensureTracker(new LandsPutOntoTheBattlefieldThisTurnCounter());
		SetGenerator number = IfThenElse.instance(LandfallForSpells.instance(), numberGenerator(3), numberGenerator(2));
		this.addEffect(drawCards(You.instance(), number, "Draw two cards.\n\nIf you had a land enter the battlefield under your control this turn, draw three cards instead."));
	}
}
