package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Naturalize")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
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
