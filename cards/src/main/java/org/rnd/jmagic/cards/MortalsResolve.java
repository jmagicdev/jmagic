package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mortal's Resolve")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class MortalsResolve extends Card
{
	public MortalsResolve(GameState state)
	{
		super(state);

		// Target creature gets +1/+1 and gains indestructible until end of
		// turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +1, +1, "Target creature gets +1/+1 and gains indestructible until end of turn.", org.rnd.jmagic.abilities.keywords.Indestructible.class));
	}
}
