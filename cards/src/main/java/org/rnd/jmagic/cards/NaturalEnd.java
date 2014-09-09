package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Natural End")
@Types({Type.INSTANT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class NaturalEnd extends Card
{
	public NaturalEnd(GameState state)
	{
		super(state);

		// Destroy target artifact or enchantment. You gain 3 life.
		SetGenerator artifactsOrEnchantments = Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance());
		SetGenerator target = targetedBy(this.addTarget(artifactsOrEnchantments, "target artifact or enchantment"));
		this.addEffect(destroy(target, "Destroy target artifact or enchantment."));
		this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
	}
}
