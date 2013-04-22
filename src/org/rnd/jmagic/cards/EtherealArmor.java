package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ethereal Armor")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class EtherealArmor extends Card
{
	public static final class EtherealArmorAbility1 extends StaticAbility
	{
		public EtherealArmorAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 for each enchantment you control and has first strike.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());
			SetGenerator X = Count.instance(Intersect.instance(EnchantmentPermanents.instance(), ControlledBy.instance(You.instance())));
			this.addEffectPart(modifyPowerAndToughness(enchanted, X, X));
			this.addEffectPart(addAbilityToObject(enchanted, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public EtherealArmor(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+1 for each enchantment you control and
		// has first strike.
		this.addAbility(new EtherealArmorAbility1(state));
	}
}
