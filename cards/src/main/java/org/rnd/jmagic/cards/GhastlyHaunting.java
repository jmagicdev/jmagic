package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ghastly Haunting")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class GhastlyHaunting extends AlternateCard
{
	public GhastlyHaunting(GameState state)
	{
		super(state);

		this.setColorIndicator(Color.BLUE);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// You control enchanted creature.
		this.addAbility(new org.rnd.jmagic.abilities.YouControlEnchantedCreature(state));
	}
}
