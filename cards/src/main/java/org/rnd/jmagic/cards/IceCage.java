package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ice Cage")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class IceCage extends Card
{
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

	public static final class Ice extends EventTriggeredAbility
	{
		public Ice(GameState state)
		{
			super(state, "When enchanted creature becomes the target of a spell or ability, destroy Ice Cage.");

			// When enchanted creature becomes the target of a spell or ability,
			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator enchantedCreature = EnchantedBy.instance(thisCard);

			EventPattern triggerPattern = new BecomesTheTargetPattern(enchantedCreature);
			this.addPattern(triggerPattern);

			// destroy Ice Cage.
			this.addEffect(destroy(ABILITY_SOURCE_OF_THIS, "Destroy Ice Cage."));
		}
	}

	public IceCage(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		this.addAbility(new Cage(state));
		this.addAbility(new Ice(state));
	}
}
