package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Smite")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STRONGHOLD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Smite extends Card
{
	public Smite(GameState state)
	{
		super(state);

		// Destroy target blocked creature.
		SetGenerator target = targetedBy(this.addTarget(Blocked.instance(), "target blocked creature"));
		this.addEffect(destroy(target, "Destroy target blocked creature."));
	}
}
