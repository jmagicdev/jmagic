package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Back to Nature")
@Types({Type.INSTANT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class BacktoNature extends Card
{
	public BacktoNature(GameState state)
	{
		super(state);

		this.addEffect(destroy(EnchantmentPermanents.instance(), "Destroy all enchantments."));
	}
}
