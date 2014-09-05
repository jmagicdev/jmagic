package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Arrest")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Arrest extends Card
{
	/**
	 * Copied directly from IceCage.Cage
	 */
	public static final class Cage extends StaticAbility
	{
		public Cage(GameState state)
		{
			super(state, "Enchanted creature can't attack or block, and its activated abilities can't be activated.");

			// Enchanted creature
			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			// can't attack
			ContinuousEffect.Part restrictions = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			restrictions.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(enchantedCreature, Attacking.instance())));
			this.addEffectPart(restrictions);

			// or block,
			ContinuousEffect.Part block = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			block.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(enchantedCreature, Blocking.instance())));
			this.addEffectPart(block);

			// and its activated abilities can't be activated.
			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(enchantedCreature));

			ContinuousEffect.Part prohibition = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			prohibition.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(prohibition);
		}
	}

	public Arrest(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature can't attack or block, and its activated abilities
		// can't be activated.
		this.addAbility(new Cage(state));
	}
}
