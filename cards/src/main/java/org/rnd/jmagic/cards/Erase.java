package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Erase")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.COMMON)})
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
