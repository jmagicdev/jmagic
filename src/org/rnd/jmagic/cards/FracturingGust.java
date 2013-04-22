package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fracturing Gust")
@Types({Type.INSTANT})
@ManaCost("2(G/W)(G/W)(G/W)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class FracturingGust extends Card
{
	public FracturingGust(GameState state)
	{
		super(state);

		// Destroy all artifacts and enchantments.
		EventFactory destroy = destroy(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "Destroy artifacts and enchantments.");
		this.addEffect(destroy);

		// You gain 2 life for each permanent destroyed this way.
		SetGenerator X = Count.instance(NewObjectOf.instance(EffectResult.instance(destroy)));
		this.addEffect(gainLife(You.instance(), Multiply.instance(numberGenerator(2), X), "You gain 2 life for each permanent destroyed this way."));
	}
}
