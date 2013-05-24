package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pursuit of Flight")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class PursuitofFlight extends Card
{
	public static final class PursuitofFlightAbility1 extends StaticAbility
	{
		public static final class GainFlying extends ActivatedAbility
		{
			public GainFlying(GameState state)
			{
				super(state, "(U): This creature gains flying until end of turn.");
				this.setManaCost(new ManaPool("(U)"));

				this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "This creature gains flying until end of turn."));
			}
		}

		public PursuitofFlightAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has \"(U): This creature gains flying until end of turn.\"");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchanted, +2, +2));
			this.addEffectPart(addAbilityToObject(enchanted, GainFlying.class));
		}
	}

	public PursuitofFlight(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has
		// "(U): This creature gains flying until end of turn."
		this.addAbility(new PursuitofFlightAbility1(state));
	}
}
