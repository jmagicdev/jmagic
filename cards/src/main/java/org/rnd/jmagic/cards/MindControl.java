package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mind Control")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class MindControl extends Card
{
	public MindControl(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new org.rnd.jmagic.abilities.YouControlEnchantedCreature(state));
	}
}
