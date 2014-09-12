package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pillar of Light")
@Types({Type.INSTANT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class PillarofLight extends Card
{
	public PillarofLight(GameState state)
	{
		super(state);

		// Exile target creature with toughness 4 or greater.
		SetGenerator target = targetedBy(this.addTarget(HasToughness.instance(Between.instance(4, null)), "target creature with toughness 4 or greater"));
		this.addEffects(exile(target, "Exile target creature with toughness 4 or greater."));
	}
}
