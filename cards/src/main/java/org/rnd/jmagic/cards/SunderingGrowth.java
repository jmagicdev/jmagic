package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sundering Growth")
@Types({Type.INSTANT})
@ManaCost("(G/W)(G/W)")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SunderingGrowth extends Card
{
	public SunderingGrowth(GameState state)
	{
		super(state);

		// Destroy target artifact or enchantment,
		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));
		this.addEffect(destroy(target, "Destroy target artifact or enchantment."));

		// then populate.
		this.addEffect(populate("then populate."));
	}
}
