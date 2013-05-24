package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Volcanic Geyser")
@Types({Type.INSTANT})
@ManaCost("XRR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.UNCOMMON)})
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
