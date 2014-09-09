package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Drake Umbra")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class DrakeUmbra extends Card
{
	public static final class DrakePump extends StaticAbility
	{
		public DrakePump(GameState state)
		{
			super(state, "Enchanted creature gets +3/+3 and has flying.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +3, +3));

			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public DrakeUmbra(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +3/+3 and has flying.
		this.addAbility(new DrakePump(state));

		// Totem armor
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TotemArmor(state));
	}
}
