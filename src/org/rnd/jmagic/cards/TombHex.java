package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tomb Hex")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class TombHex extends Card
{
	public TombHex(GameState state)
	{
		super(state);

		// Target creature gets -2/-2 until end of turn.

		// Landfall \u2014 If you had a land enter the battlefield under your
		// control this turn, that creature gets -4/-4 until end of turn
		// instead.

		SetGenerator t = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		state.ensureTracker(new LandsPutOntoTheBattlefieldThisTurnCounter());
		SetGenerator amount = IfThenElse.instance(LandfallForSpells.instance(), numberGenerator(-4), numberGenerator(-2));

		String effectName = "Target creature gets -2/-2 until end of turn.\n\nIf you had a land enter the battlefield under your control this turn, that creature gets -4/-4 until end of turn instead.";
		this.addEffect(createFloatingEffect(effectName, modifyPowerAndToughness(t, amount, amount)));
	}
}
