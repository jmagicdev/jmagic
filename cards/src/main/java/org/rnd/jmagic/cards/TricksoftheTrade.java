package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tricks of the Trade")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class TricksoftheTrade extends Card
{
	public static final class TricksoftheTradeAbility1 extends StaticAbility
	{
		public TricksoftheTradeAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+0 and can't be blocked.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchanted, +2, +0));
			this.addEffectPart(unblockable(enchanted));
		}
	}

	public TricksoftheTrade(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+0 and is unblockable.
		this.addAbility(new TricksoftheTradeAbility1(state));
	}
}
