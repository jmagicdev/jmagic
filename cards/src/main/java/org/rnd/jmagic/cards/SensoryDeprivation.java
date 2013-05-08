package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sensory Deprivation")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SensoryDeprivation extends Card
{
	public static final class SensoryDeprivationAbility1 extends StaticAbility
	{
		public SensoryDeprivationAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -3/-0.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), -3, -0));
		}
	}

	public SensoryDeprivation(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -3/-0.
		this.addAbility(new SensoryDeprivationAbility1(state));
	}
}
