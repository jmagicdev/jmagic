package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Furor of the Bitten")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FuroroftheBitten extends Card
{
	public static final class FuroroftheBittenAbility1 extends StaticAbility
	{
		public FuroroftheBittenAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and attacks each turn if able.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchanted, +2, +2));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, enchanted);
			this.addEffectPart(part);
		}
	}

	public FuroroftheBitten(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and attacks each turn if able.
		this.addAbility(new FuroroftheBittenAbility1(state));
	}
}
