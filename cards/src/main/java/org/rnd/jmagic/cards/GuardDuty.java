package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Guard Duty")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GuardDuty extends Card
{
	public static final class GrantDefender extends StaticAbility
	{
		public GrantDefender(GameState state)
		{
			super(state, "Enchanted creature has defender.");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Defender.class));
		}
	}

	public GuardDuty(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has defender.
		this.addAbility(new GrantDefender(state));
	}
}
