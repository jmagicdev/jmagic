package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Distortion Strike")
@Types({Type.SORCERY})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class DistortionStrike extends Card
{
	public DistortionStrike(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		// Target creature gets +1/+0 until end of turn and is unblockable this
		// turn.
		this.addEffect(createFloatingEffect("Target creature gets +1/+0 until end of turn and can't be blocked this turn.", modifyPowerAndToughness(targetedBy(target), +1, +0), unblockable(targetedBy(target))));

		// Rebound
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Rebound(state));
	}
}
