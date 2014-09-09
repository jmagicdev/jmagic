package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Volcanic Geyser")
@Types({Type.INSTANT})
@ManaCost("XRR")
@ColorIdentity({Color.RED})
public final class VolcanicGeyser extends Card
{
	public VolcanicGeyser(GameState state)
	{
		super(state);

		// Volcanic Geyser deals X damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), target, "Volcanic Geyser deals X damage to target creature or player."));
	}
}
