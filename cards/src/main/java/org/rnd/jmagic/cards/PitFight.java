package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Pit Fight")
@Types({Type.INSTANT})
@ManaCost("1(R/G)")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class PitFight extends Card
{
	public PitFight(GameState state)
	{
		super(state);

		// Target creature you control fights another target creature. (Each
		// deals damage equal to its power to the other.)
		Target firstTarget = this.addTarget(CREATURES_YOU_CONTROL, "target creature you control");
		firstTarget.restrictFromLaterTargets = true;

		Target secondTarget = this.addTarget(CreaturePermanents.instance(), "another target creature");

		this.addEffect(fight(Union.instance(targetedBy(firstTarget), targetedBy(secondTarget)), "Target creature you control fights another target creature."));
	}
}
