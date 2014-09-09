package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Defensive Stance")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class DefensiveStance extends Card
{
	public static final class DefensiveStanceAbility1 extends StaticAbility
	{
		public DefensiveStanceAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -1/+1.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), -1, +1));
		}
	}

	public DefensiveStance(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -1/+1.
		this.addAbility(new DefensiveStanceAbility1(state));
	}
}
