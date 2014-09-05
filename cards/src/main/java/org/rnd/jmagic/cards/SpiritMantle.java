package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Spirit Mantle")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class SpiritMantle extends Card
{
	public static final class SpiritMantleAbility1 extends StaticAbility
	{
		public SpiritMantleAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and has protection from creatures.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Protection.FromCreatures.class));
		}
	}

	public SpiritMantle(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+1 and has protection from creatures. (It
		// can't be blocked, targeted, or dealt damage by creatures.)
		this.addAbility(new SpiritMantleAbility1(state));
	}
}
