package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Debilitating Injury")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class DebilitatingInjury extends Card
{
	public static final class DebilitatingInjuryAbility1 extends StaticAbility
	{
		public DebilitatingInjuryAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -2/-2.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), -2, -2));
		}
	}

	public DebilitatingInjury(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -2/-2.
		this.addAbility(new DebilitatingInjuryAbility1(state));
	}
}
