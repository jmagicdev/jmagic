package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Relic Crush")
@Types({Type.INSTANT})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class RelicCrush extends Card
{
	public RelicCrush(GameState state)
	{
		super(state);

		// Destroy target artifact or enchantment and up to one other target
		// artifact or enchantment.
		SetGenerator legalTargets = Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance());
		Target target = this.addTarget(legalTargets, "one or two target artifacts and/or enchantments");
		target.setNumber(1, 2);

		this.addEffect(destroy(targetedBy(target), "Destroy target artifact or enchantment and up to one other target artifact or enchantment."));
	}
}
