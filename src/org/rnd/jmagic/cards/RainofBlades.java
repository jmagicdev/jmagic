package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rain of Blades")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class RainofBlades extends Card
{
	public RainofBlades(GameState state)
	{
		super(state);

		// Rain of Blades deals 1 damage to each attacking creature.
		this.addEffect(spellDealDamage(1, Attacking.instance(), "Rain of Blades deals 1 damage to each attacking creature."));
	}
}
