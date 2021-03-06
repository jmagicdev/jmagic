package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Solemn Offering")
@Types({Type.SORCERY})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class SolemnOffering extends Card
{
	public SolemnOffering(GameState state)
	{
		super(state);

		Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
		this.addEffect(destroy(targetedBy(target), "Destroy target artifact or enchantment."));

		this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
	}
}
