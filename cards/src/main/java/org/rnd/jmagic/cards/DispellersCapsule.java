package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dispeller's Capsule")
@Types({Type.ARTIFACT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class DispellersCapsule extends Card
{
	public static final class Disenchant extends ActivatedAbility
	{
		public Disenchant(GameState state)
		{
			super(state, "(2)(W), (T), Sacrifice Dispeller's Capsule: Destroy target artifact or enchantment.");
			this.setManaCost(new ManaPool("2W"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Dispeller's Capsule"));

			Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
			this.addEffect(destroy(targetedBy(target), "Destroy target artifact or enchantment."));
		}
	}

	public DispellersCapsule(GameState state)
	{
		super(state);

		// (2)(W), (T), Sacrifice Dispeller's Capsule: Destroy target artifact
		// or enchantment.
		this.addAbility(new Disenchant(state));
	}
}
