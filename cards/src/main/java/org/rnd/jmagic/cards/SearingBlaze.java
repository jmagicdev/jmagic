package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Searing Blaze")
@Types({Type.INSTANT})
@ManaCost("RR")
@ColorIdentity({Color.RED})
public final class SearingBlaze extends Card
{
	public SearingBlaze(GameState state)
	{
		super(state);

		// Searing Blaze deals 1 damage to target player and 1 damage to target
		// creature that player controls.

		// Landfall \u2014 If you had a land enter the battlefield under your
		// control this turn, Searing Blaze deals 3 damage to that player and 3
		// damage to that creature instead.

		state.ensureTracker(new LandsPutOntoTheBattlefieldThisTurnCounter());
		SetGenerator damageAmount = IfThenElse.instance(LandfallForSpells.instance(), numberGenerator(3), numberGenerator(1));

		Target targetPlayer = this.addTarget(Players.instance(), "target player");
		Target targetCreature = this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(targetedBy(targetPlayer))), "target creature that player controls");

		String effectName = "Searing Blaze deals 1 damage to target player and 1 damage to target creature that player controls.\n\nIf you had a land enter the battlefield under your control this turn, Searing Blaze deals 3 damage to that player and 3 damage to that creature instead.";
		this.addEffect(spellDealDamage(damageAmount, targetedBy(targetPlayer, targetCreature), effectName));
	}
}
