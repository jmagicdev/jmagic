package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lure")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Lure extends Card
{
	// All creatures able to block enchanted creature do so.
	public static final class Taunt extends StaticAbility
	{
		public Taunt(GameState state)
		{
			super(state, "All creatures able to block enchanted creature do so.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT_FOR_EACH_DEFENDING_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, EnchantedBy.instance(This.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, HasType.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public Lure(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new Taunt(state));
	}
}
