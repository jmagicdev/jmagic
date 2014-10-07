package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Consign to Dust")
@Types({Type.INSTANT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class ConsigntoDust extends Card
{
	public ConsigntoDust(GameState state)
	{
		super(state);

		// Strive \u2014 Consign to Dust costs (2)(G) more to cast for each
		// target beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(2)(G)"));

		// Destroy any number of target artifacts and/or enchantments.
		Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "any number of target artifacts and/or enchantments").setNumber(0, null);
		this.addEffect(destroy(targetedBy(target), "Destroy any number of target artifacts and/or enchantments."));
	}
}
