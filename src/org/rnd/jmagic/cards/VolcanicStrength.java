package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Volcanic Strength")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class VolcanicStrength extends Card
{
	public final static class VolcanicStrengthAbility0 extends StaticAbility
	{
		public VolcanicStrengthAbility0(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has mountainwalk.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +2));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Landwalk.Mountainwalk.class));
		}
	}

	public VolcanicStrength(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has mountainwalk. (It's unblockable
		// as long as defending player controls a Mountain.)
		this.addAbility(new VolcanicStrengthAbility0(state));
	}
}
