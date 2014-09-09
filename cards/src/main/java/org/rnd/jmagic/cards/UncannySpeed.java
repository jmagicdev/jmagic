package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Uncanny Speed")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class UncannySpeed extends Card
{
	public UncannySpeed(GameState state)
	{
		super(state);

		// Target creature gets +3/+0 and gains haste until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +3, +0, "Target creature gets +3/+0 and gains haste until end of turn.", org.rnd.jmagic.abilities.keywords.Haste.class));
	}
}
