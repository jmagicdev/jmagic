package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Awaken the Bear")
@Types({Type.INSTANT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class AwakentheBear extends Card
{
	public AwakentheBear(GameState state)
	{
		super(state);

		// Target creature gets +3/+3 and gains trample until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +3, +3, "Target creature gets +3/+3 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
