package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Joint Assault")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class JointAssault extends Card
{
	public JointAssault(GameState state)
	{
		super(state);

		// Target creature gets +2/+2 until end of turn. If it's paired with a
		// creature, that creature also gets +2/+2 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator creatures = Union.instance(target, PairedWith.instance(target));
		this.addEffect(createFloatingEffect("Target creature gets +2/+2 until end of turn. If it's paired with a creature, that creature also gets +2/+2 until end of turn.", modifyPowerAndToughness(creatures, +2, +2)));
	}
}
