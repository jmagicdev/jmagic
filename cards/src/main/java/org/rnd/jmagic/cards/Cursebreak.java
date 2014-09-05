package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cursebreak")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Cursebreak extends Card
{
	public Cursebreak(GameState state)
	{
		super(state);

		// Destroy target enchantment. You gain 2 life.
		SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
		this.addEffect(destroy(target, "Destroy target enchantment."));

		this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
	}
}
