package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Weight of the Underworld")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class WeightoftheUnderworld extends Card
{
	public static final class WeightoftheUnderworldAbility1 extends StaticAbility
	{
		public WeightoftheUnderworldAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -3/-2.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), -3, -2));
		}
	}

	public WeightoftheUnderworld(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -3/-2.
		this.addAbility(new WeightoftheUnderworldAbility1(state));
	}
}
