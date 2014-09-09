package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spider Umbra")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class SpiderUmbra extends Card
{
	public static final class SpiderUmbraAbility1 extends StaticAbility
	{
		public SpiderUmbraAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 and has reach.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Reach.class));
		}
	}

	public SpiderUmbra(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+1 and has reach. (It can block creatures
		// with flying.)
		this.addAbility(new SpiderUmbraAbility1(state));

		// Totem armor (If enchanted creature would be destroyed, instead remove
		// all damage from it and destroy this Aura.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TotemArmor(state));
	}
}
