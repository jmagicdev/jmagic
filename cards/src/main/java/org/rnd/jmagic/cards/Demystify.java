package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Demystify")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
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
