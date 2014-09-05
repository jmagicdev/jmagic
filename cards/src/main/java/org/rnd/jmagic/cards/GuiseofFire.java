package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Guise of Fire")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("R")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GuiseofFire extends Card
{
	public static final class GuiseofFireAbility1 extends StaticAbility
	{
		public GuiseofFireAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/-1 and attacks each turn if able.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +1, -1));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, enchantedCreature);
			this.addEffectPart(part);
		}
	}

	public GuiseofFire(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/-1 and attacks each turn if able.
		this.addAbility(new GuiseofFireAbility1(state));
	}
}
