package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unholy Strength")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class UnholyStrength extends Card
{
	public UnholyStrength(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, enchantedCreature, "Enchanted creature", +2, +1, false));
	}
}
