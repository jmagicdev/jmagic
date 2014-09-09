package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nature's Claim")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class NaturesClaim extends Card
{
	public NaturesClaim(GameState state)
	{
		super(state);

		// Destroy target artifact or enchantment.
		Target t = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
		this.addEffect(destroy(targetedBy(t), "Destroy target artifact or enchantment."));

		// Its controller gains 4 life.
		this.addEffect(gainLife(ControllerOf.instance(targetedBy(t)), 4, "Its controller gains 4 life."));
	}
}
