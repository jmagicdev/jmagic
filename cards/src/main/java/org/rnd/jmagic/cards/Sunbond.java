package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Sunbond")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class Sunbond extends Card
{
	public static final class Bonded extends EventTriggeredAbility
	{
		public Bonded(GameState state)
		{
			super(state, "Whenever you gain life, put that many +1/+1 counters on this creature.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(pattern);

			SetGenerator thatMany = Sum.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.NUMBER));
			this.addEffect(putCounters(thatMany, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put that many +1/+1 counters on this creature."));
		}
	}

	public static final class SunbondAbility1 extends StaticAbility
	{
		public SunbondAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"Whenever you gain life, put that many +1/+1 counters on this creature.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), Bonded.class));
		}
	}

	public Sunbond(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has
		// "Whenever you gain life, put that many +1/+1 counters on this creature."
		this.addAbility(new SunbondAbility1(state));
	}
}
