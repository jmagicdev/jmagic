package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glimpse the Sun God")
@Types({Type.INSTANT})
@ManaCost("XW")
@ColorIdentity({Color.WHITE})
public final class GlimpsetheSunGod extends Card
{
	public GlimpsetheSunGod(GameState state)
	{
		super(state);

		// Tap X target creatures.
		SetGenerator X = ValueOfX.instance(This.instance());
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "X target creatures").setSingleNumber(X));
		this.addEffect(tap(target, "Tap X target creatures."));

		// Scry 1.
		this.addEffect(scry(1, "Scry 1."));
	}
}
