package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Arc Trail")
@Types({Type.SORCERY})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class ArcTrail extends Card
{
	public ArcTrail(GameState state)
	{
		super(state);

		// Arc Trail deals 2 damage to target creature or player and 1 damage to
		// another target creature or player.
		Target firstTarget = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		firstTarget.restrictFromLaterTargets = true;
		SetGenerator targetOne = targetedBy(firstTarget);
		this.addEffect(spellDealDamage(2, targetOne, "Arc Trail deals 2 damage to target creature or player"));

		SetGenerator targetTwo = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "another target creature or player"));
		this.addEffect(spellDealDamage(1, targetTwo, "and 1 damage to another target creature or player."));
	}
}
