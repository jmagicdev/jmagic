package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Veteran's Reflexes")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class VeteransReflexes extends Card
{
	public VeteransReflexes(GameState state)
	{
		super(state);
		SetGenerator t = targetedBy(this.addTarget(CreaturePermanents.instance(), "creature"));

		// Target creature gets +1/+1 until end of turn. Untap that creature.
		this.addEffect(createFloatingEffect("Target creature gets +1/+1 until end of turn.", modifyPowerAndToughness(t, 1, 1)));
		this.addEffect(untap(t, "Untap that creature."));
	}
}
