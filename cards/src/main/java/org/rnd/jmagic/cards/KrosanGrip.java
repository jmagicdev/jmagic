package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Krosan Grip")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class KrosanGrip extends Card
{
	public KrosanGrip(GameState state)
	{
		super(state);

		// Split second
		this.addAbility(new org.rnd.jmagic.abilities.keywords.SplitSecond(state));

		// Destroy target artifact or enchantment.
		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));
		this.addEffect(destroy(target, "Destroy target artifact or enchantment."));
	}
}
