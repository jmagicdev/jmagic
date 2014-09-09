package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Disenchant")
@Types({Type.INSTANT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class Disenchant extends Card
{
	public Disenchant(GameState state)
	{
		super(state);

		// Destroy target artifact or enchantment.
		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));
		this.addEffect(destroy(target, "Destroy target artifact or enchantment."));
	}
}
