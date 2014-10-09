package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nyx Infusion")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class NyxInfusion extends Card
{
	public static final class NyxInfusionAbility1 extends StaticAbility
	{
		public NyxInfusionAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 as long as it's an enchantment. Otherwise, it gets -2/-2.");

			SetGenerator isEnchantment = Intersect.instance(EnchantmentPermanents.instance(), EnchantedBy.instance(This.instance()));
			SetGenerator amount = IfThenElse.instance(isEnchantment, numberGenerator(+2), numberGenerator(-2));
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), amount, amount));
		}
	}

	public NyxInfusion(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 as long as it's an enchantment.
		// Otherwise, it gets -2/-2.
		this.addAbility(new NyxInfusionAbility1(state));
	}
}
