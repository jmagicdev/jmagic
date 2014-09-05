package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rain of Blades")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Scourge.class, r = Rarity.UNCOMMON)})
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
