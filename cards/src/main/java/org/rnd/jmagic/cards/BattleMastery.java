package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Battle Mastery")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class BattleMastery extends Card
{
	public static final class BattleMasteryAbility1 extends StaticAbility
	{
		public BattleMasteryAbility1(GameState state)
		{
			super(state, "Enchanted creature has double strike.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.DoubleStrike.class));
		}
	}

	public BattleMastery(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has double strike. (It deals both first-strike and
		// regular combat damage.)
		this.addAbility(new BattleMasteryAbility1(state));
	}
}
