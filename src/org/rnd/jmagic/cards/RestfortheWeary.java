package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rest for the Weary")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class RestfortheWeary extends Card
{
	public RestfortheWeary(GameState state)
	{
		super(state);

		// Target player gains 4 life.
		// If you had a land enter the battlefield under your control this turn,
		// that player gains 8 life instead.
		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator amount = IfThenElse.instance(LandfallForSpells.instance(), numberGenerator(8), numberGenerator(4));
		state.ensureTracker(new LandsPutOntoTheBattlefieldThisTurnCounter());
		this.addEffect(gainLife(targetedBy(target), amount, "Target player gains 4 life.\n\nIf you had a land enter the battlefield under your control this turn, that player gains 8 life instead."));
	}
}
