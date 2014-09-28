package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Molting Snakeskin")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class MoltingSnakeskin extends Card
{
	public static final class Regenerate2B extends org.rnd.jmagic.abilities.Regenerate
	{
		public Regenerate2B(GameState state)
		{
			super(state, "(2)(B)", "this creature");
		}
	}

	public static final class MoltingSnakeskinAbility1 extends StaticAbility
	{
		public MoltingSnakeskinAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+0 and has \"(2)(B): Regenerate this creature.\"");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +2, +0));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), Regenerate2B.class));
		}
	}

	public MoltingSnakeskin(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+0 and has
		// "(2)(B): Regenerate this creature."
		this.addAbility(new MoltingSnakeskinAbility1(state));
	}
}
