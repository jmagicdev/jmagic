package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Threads of Disloyalty")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.BETRAYERS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ThreadsofDisloyalty extends Card
{
	public static final class EnchantSmallCreature extends org.rnd.jmagic.abilities.keywords.Enchant
	{
		private static SetGenerator smallCreatures()
		{
			SetGenerator small = HasConvertedManaCost.instance(Between.instance(null, 2));
			return Intersect.instance(CreaturePermanents.instance(), small);
		}

		public EnchantSmallCreature(GameState state)
		{
			super(state, "creature with converted mana cost 2 or less", smallCreatures());
		}
	}

	public ThreadsofDisloyalty(GameState state)
	{
		super(state);

		// Enchant creature with converted mana cost 2 or less
		this.addAbility(new EnchantSmallCreature(state));

		// You control enchanted creature.
		this.addAbility(new org.rnd.jmagic.abilities.YouControlEnchantedCreature(state));
	}
}
