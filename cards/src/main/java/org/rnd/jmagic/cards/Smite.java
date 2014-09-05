package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Smite")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON), @Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON), @Printings.Printed(ex = Stronghold.class, r = Rarity.COMMON)})
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
