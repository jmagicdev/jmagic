package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Volcanic Geyser")
@Types({Type.INSTANT})
@ManaCost("XRR")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Mirage.class, r = Rarity.UNCOMMON)})
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
