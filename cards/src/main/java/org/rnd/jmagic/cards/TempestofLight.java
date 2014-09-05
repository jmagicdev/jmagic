package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tempest of Light")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class TempestofLight extends Card
{
	public TempestofLight(GameState state)
	{
		super(state);

		this.addEffect(destroy(EnchantmentPermanents.instance(), "Destroy all enchantments."));
	}
}
