package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spectral Flight")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SpectralFlight extends Card
{
	public static final class SpectralFlightAbility1 extends StaticAbility
	{
		public SpectralFlightAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has flying.");

			SetGenerator creature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(creature, +2, +2));

			this.addEffectPart(addAbilityToObject(creature, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public SpectralFlight(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has flying.
		this.addAbility(new SpectralFlightAbility1(state));
	}
}
