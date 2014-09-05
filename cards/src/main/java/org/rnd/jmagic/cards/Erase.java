package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Erase")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasLegacy.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Erase extends Card
{
	public Erase(GameState state)
	{
		super(state);
		// Exile target enchantment.
		Target target = this.addTarget(EnchantmentPermanents.instance(), "target enchantment.");
		this.addEffect(exile(targetedBy(target), "Exile target enchantment."));
	}
}
