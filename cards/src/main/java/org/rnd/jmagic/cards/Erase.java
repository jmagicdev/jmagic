package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Erase")
@Types({Type.INSTANT})
@ManaCost("W")
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
