package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Abundant Growth")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class AbundantGrowth extends Card
{
	public static final class AbundantGrowthAbility1 extends EventTriggeredAbility
	{
		public AbundantGrowthAbility1(GameState state)
		{
			super(state, "When Abundant Growth enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public static final class AbundantGrowthAbility2 extends StaticAbility
	{
		public AbundantGrowthAbility2(GameState state)
		{
			super(state, "Enchanted land has \"(T): Add one mana of any color to your mana pool.\"");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.TapForAnyColor.class));
		}
	}

	public AbundantGrowth(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// When Abundant Growth enters the battlefield, draw a card.
		this.addAbility(new AbundantGrowthAbility1(state));

		// Enchanted land has
		// "(T): Add one mana of any color to your mana pool."
		this.addAbility(new AbundantGrowthAbility2(state));
	}
}
