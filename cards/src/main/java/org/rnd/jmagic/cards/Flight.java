package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flight")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class Flight extends Card
{
	public static final class FlightAbility1 extends StaticAbility
	{
		public FlightAbility1(GameState state)
		{
			super(state, "Enchanted creature has flying.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public Flight(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has flying.
		this.addAbility(new FlightAbility1(state));
	}
}
