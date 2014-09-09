package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phyresis")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class Phyresis extends Card
{
	public static final class PhyresisAbility1 extends StaticAbility
	{
		public PhyresisAbility1(GameState state)
		{
			super(state, "Enchanted creature has infect.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Infect.class));
		}
	}

	public Phyresis(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has infect. (It deals damage to creatures in the
		// form of -1/-1 counters and to players in the form of poison
		// counters.)
		this.addAbility(new PhyresisAbility1(state));
	}
}
