package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demystify")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class Demystify extends Card
{
	public Demystify(GameState state)
	{
		super(state);

		Target target = this.addTarget(EnchantmentPermanents.instance(), "target enchantment");
		this.addEffect(destroy(targetedBy(target), "Destroy target enchantment."));
	}
}
