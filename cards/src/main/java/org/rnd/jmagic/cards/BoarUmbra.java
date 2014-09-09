package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Boar Umbra")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class BoarUmbra extends Card
{
	public static final class BoarPump extends StaticAbility
	{
		public BoarPump(GameState state)
		{
			super(state, "Enchanted creature gets +3/+3.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +3, +3));
		}
	}

	public BoarUmbra(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +3/+3.
		this.addAbility(new BoarPump(state));

		// Totem armor
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TotemArmor(state));
	}
}
