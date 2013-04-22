package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dead Weight")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DeadWeight extends Card
{
	public static final class DeadWeightAbility1 extends StaticAbility
	{
		public DeadWeightAbility1(GameState state)
		{
			super(state, "Enchanted creature gets -2/-2.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), -2, -2));
		}
	}

	public DeadWeight(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets -2/-2.
		this.addAbility(new DeadWeightAbility1(state));
	}
}
