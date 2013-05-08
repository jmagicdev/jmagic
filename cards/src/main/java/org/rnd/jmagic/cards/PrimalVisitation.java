package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Primal Visitation")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3RG")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class PrimalVisitation extends Card
{
	public static final class PrimalVisitationAbility1 extends StaticAbility
	{
		public PrimalVisitationAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +3/+3 and has haste.");
			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +3, +3));
			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public PrimalVisitation(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +3/+3 and has haste.
		this.addAbility(new PrimalVisitationAbility1(state));
	}
}
