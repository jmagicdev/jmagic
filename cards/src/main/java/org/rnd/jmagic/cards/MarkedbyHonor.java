package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Marked by Honor")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class MarkedbyHonor extends Card
{
	public static final class MarkedbyHonorAbility1 extends StaticAbility
	{
		public MarkedbyHonorAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has vigilance.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +2));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public MarkedbyHonor(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has vigilance. (Attacking doesn't
		// cause it to tap.)
		this.addAbility(new MarkedbyHonorAbility1(state));
	}
}
