package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Naturalize")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Naturalize extends Card
{
	public Naturalize(GameState state)
	{
		super(state);

		Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
		this.addEffect(destroy(targetedBy(target), "Destroy target artifact or enchantment."));
	}
}
