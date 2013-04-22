package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Eland Umbra")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ElandUmbra extends Card
{
	public static final class ElandPump extends StaticAbility
	{
		public ElandPump(GameState state)
		{
			super(state, "Enchanted creature gets +0/+4.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +0, +4));
		}
	}

	public ElandUmbra(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +0/+4.
		this.addAbility(new ElandPump(state));

		// Totem armor
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TotemArmor(state));
	}
}
