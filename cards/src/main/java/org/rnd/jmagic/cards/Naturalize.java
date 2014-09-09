package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Naturalize")
@Types({Type.INSTANT})
@ManaCost("1G")
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
