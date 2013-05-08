package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arcane Teachings")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ArcaneTeachings extends Card
{
	public static final class BigPinger extends StaticAbility
	{
		public BigPinger(GameState state)
		{
			super(state, "Enchanted creature gets +2/+2 and has \"(T): This creature deals 1 damage to target creature or player.\"");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, 2, 2));
			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.Ping.class));
		}
	}

	public ArcaneTeachings(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new BigPinger(state));
	}
}
