package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Slice in Twain")
@Types({Type.INSTANT})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class SliceinTwain extends Card
{
	public SliceinTwain(GameState state)
	{
		super(state);

		// Destroy target artifact or enchantment.
		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));
		this.addEffect(destroy(target, "Destroy target artifact or enchantment."));

		// Draw a card.
		this.addEffect(drawACard());
	}
}
