package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Holy Strength")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
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
