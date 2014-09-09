package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Arachnus Web")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class ArachnusWeb extends Card
{
	public static final class ArachnusWebAbility1 extends StaticAbility
	{
		public ArachnusWebAbility1(GameState state)
		{
			super(state, "Enchanted creature can't attack or block, and its activated abilities can't be activated.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part1 = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part1.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Attacking.instance(), enchantedCreature)));
			this.addEffectPart(part1);

			ContinuousEffect.Part part2 = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part2.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), enchantedCreature)));
			this.addEffectPart(part2);

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(enchantedCreature));

			ContinuousEffect.Part part3 = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part3.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part3);
		}
	}

	public static final class ArachnusWebAbility2 extends EventTriggeredAbility
	{
		public ArachnusWebAbility2(GameState state)
		{
			super(state, "At the beginning of the end step, if enchanted creature's power is 4 or greater, destroy Arachnus Web.");
			this.addPattern(atTheBeginningOfTheEndStep());

			SetGenerator power = PowerOf.instance(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));
			this.interveningIf = Intersect.instance(power, Between.instance(4, null));

			this.addEffect(destroy(ABILITY_SOURCE_OF_THIS, "Destroy Arachnus Web"));
		}
	}

	public ArachnusWeb(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature can't attack or block, and its activated abilities
		// can't be activated.
		this.addAbility(new ArachnusWebAbility1(state));

		// At the beginning of the end step, if enchanted creature's power is 4
		// or greater, destroy Arachnus Web.
		this.addAbility(new ArachnusWebAbility2(state));
	}
}
