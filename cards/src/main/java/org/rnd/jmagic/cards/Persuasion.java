package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Persuasion")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class Persuasion extends Card
{
	public Persuasion(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new org.rnd.jmagic.abilities.YouControlEnchantedCreature(state));
	}
}
