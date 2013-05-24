package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Seal of Primordium")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SealofPrimordium extends Card
{
	public static final class SealofPrimordiumAbility0 extends ActivatedAbility
	{
		public SealofPrimordiumAbility0(GameState state)
		{
			super(state, "Sacrifice Seal of Primordium: Destroy target artifact or enchantment.");
			this.addCost(sacrificeThis("Seal of Primordium"));
			Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
			this.addEffect(destroy(targetedBy(target), "Destroy target artifact or enchantment."));
		}
	}

	public SealofPrimordium(GameState state)
	{
		super(state);

		// Sacrifice Seal of Primordium: Destroy target artifact or enchantment.
		this.addAbility(new SealofPrimordiumAbility0(state));
	}
}
