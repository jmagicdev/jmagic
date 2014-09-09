package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ultimate Price")
@Types({Type.INSTANT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class UltimatePrice extends Card
{
	public UltimatePrice(GameState state)
	{
		super(state);

		// Destroy target monocolored creature.
		SetGenerator t = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), Monocolored.instance()), "target monocolored creature"));
		this.addEffect(destroy(t, "Destroy target monocolored creature."));
	}
}
