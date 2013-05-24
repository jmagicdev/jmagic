package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blanchwood Armor")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BlanchwoodArmor extends Card
{
	public static final class ForestPump extends StaticAbility
	{
		public ForestPump(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 for each Forest you control.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			SetGenerator numForests = Count.instance(Intersect.instance(HasSubType.instance(SubType.FOREST), ControlledBy.instance(ControllerOf.instance(This.instance()))));

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, numForests, numForests));
		}
	}

	public BlanchwoodArmor(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new ForestPump(state));
	}
}
