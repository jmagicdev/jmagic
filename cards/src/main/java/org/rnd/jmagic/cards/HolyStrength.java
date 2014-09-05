package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Holy Strength")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class HolyStrength extends Card
{
	public HolyStrength(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, enchantedCreature, "Enchanted creature", +1, +2, false));
	}
}
