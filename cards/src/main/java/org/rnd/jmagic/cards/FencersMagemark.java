package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Fencer's Magemark")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FencersMagemark extends Card
{
	public static final class BonusesForEnchantedGuys extends StaticAbility
	{
		public BonusesForEnchantedGuys(GameState state)
		{
			super(state, "Creatures you control that are enchanted get +1/+1 and have first strike.");

			SetGenerator thingsWithAurasAttached = EnchantedBy.instance(HasSubType.instance(SubType.AURA));
			SetGenerator yourEnchantedCreatures = Intersect.instance(CREATURES_YOU_CONTROL, thingsWithAurasAttached);

			this.addEffectPart(modifyPowerAndToughness(yourEnchantedCreatures, +1, +1));
			this.addEffectPart(addAbilityToObject(yourEnchantedCreatures, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public FencersMagemark(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Creatures you control that are enchanted get +1/+1 and have first
		// strike.
		this.addAbility(new BonusesForEnchantedGuys(state));
	}
}
