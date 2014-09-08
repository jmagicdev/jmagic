package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tear")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = DragonsMaze.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Tear extends Card
{
	public Tear(GameState state)
	{
		super(state);

		Target target = this.addTarget(EnchantmentPermanents.instance(), "target enchantment");
		this.addEffect(destroy(targetedBy(target), "Destroy target enchantment."));
	}
}
