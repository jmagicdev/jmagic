package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin War Paint")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class GoblinWarPaint extends Card
{
	public static final class Paint extends StaticAbility
	{
		public Paint(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has haste.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, (+2), (+2)));
			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public GoblinWarPaint(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2 and has haste.
		this.addAbility(new Paint(state));
	}
}
