package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Persuasion")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.RARE)})
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
