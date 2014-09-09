package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tempest of Light")
@Types({Type.INSTANT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class TempestofLight extends Card
{
	public TempestofLight(GameState state)
	{
		super(state);

		this.addEffect(destroy(EnchantmentPermanents.instance(), "Destroy all enchantments."));
	}
}
