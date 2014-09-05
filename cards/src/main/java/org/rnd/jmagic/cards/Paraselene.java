package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Paraselene")
@Types({Type.SORCERY})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Paraselene extends Card
{
	public Paraselene(GameState state)
	{
		super(state);

		// Destroy all enchantments. You gain 1 life for each enchantment
		// destroyed this way.
		EventFactory destroy = destroy(EnchantmentPermanents.instance(), "Destroy all enchantments.");
		this.addEffect(destroy);

		this.addEffect(gainLife(You.instance(), Count.instance(EffectResult.instance(destroy)), "You gain 1 life for each enchantment destroyed this way."));
	}
}
