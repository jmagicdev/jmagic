package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stratus Walk")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class StratusWalk extends Card
{
	public static final class StratusWalkAbility1 extends EventTriggeredAbility
	{
		public StratusWalkAbility1(GameState state)
		{
			super(state, "When Stratus Walk enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public static final class StratusWalkAbility2 extends StaticAbility
	{
		public StratusWalkAbility2(GameState state)
		{
			super(state, "Enchanted creature has flying.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public static final class StratusWalkAbility3 extends StaticAbility
	{
		public StratusWalkAbility3(GameState state)
		{
			super(state, "Enchanted creature can block only creatures with flying.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator thisIsBlocking = BlockedBy.instance(EnchantedBy.instance(This.instance()));
			SetGenerator blockingNonFlyer = RelativeComplement.instance(thisIsBlocking, hasFlying);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingNonFlyer));
			this.addEffectPart(part);
		}
	}

	public StratusWalk(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Stratus Walk enters the battlefield, draw a card.
		this.addAbility(new StratusWalkAbility1(state));

		// Enchanted creature has flying.
		this.addAbility(new StratusWalkAbility2(state));

		// Enchanted creature can block only creatures with flying.
		this.addAbility(new StratusWalkAbility3(state));
	}
}
