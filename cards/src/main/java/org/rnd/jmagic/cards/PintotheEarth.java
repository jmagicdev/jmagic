package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pin to the Earth")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class PintotheEarth extends Card
{
	public static final class PintotheEarthAbility1 extends StaticAbility
	{
		public PintotheEarthAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -6/-0.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), -6, -0));
		}
	}

	public PintotheEarth(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -6/-0.
		this.addAbility(new PintotheEarthAbility1(state));
	}
}
