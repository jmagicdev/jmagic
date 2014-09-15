package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern;

@Name("Ephara's Enlightenment")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class EpharasEnlightenment extends Card
{
	public static final class EpharasEnlightenmentAbility1 extends EventTriggeredAbility
	{
		public EpharasEnlightenmentAbility1(GameState state)
		{
			super(state, "When Ephara's Enlightenment enters the battlefield, put a +1/+1 counter on enchanted creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, EnchantedBy.instance(This.instance()), "Put a +1/+1 counter on enchanted creature."));
		}
	}

	public static final class EpharasEnlightenmentAbility2 extends StaticAbility
	{
		public EpharasEnlightenmentAbility2(GameState state)
		{
			super(state, "Enchanted creature has flying.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public static final class EpharasEnlightenmentAbility3 extends EventTriggeredAbility
	{
		public EpharasEnlightenmentAbility3(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under your control, you may return Ephara's Enlightenment to its owner's hand.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasType.instance(Type.CREATURE), You.instance(), false));
			this.addEffect(youMay(bounce(ABILITY_SOURCE_OF_THIS, "Return Ephara's Enlightenment to its owner's hand")));
		}
	}

	public EpharasEnlightenment(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Ephara's Enlightenment enters the battlefield, put a +1/+1
		// counter on enchanted creature.
		this.addAbility(new EpharasEnlightenmentAbility1(state));

		// Enchanted creature has flying.
		this.addAbility(new EpharasEnlightenmentAbility2(state));

		// Whenever a creature enters the battlefield under your control, you
		// may return Ephara's Enlightenment to its owner's hand.
		this.addAbility(new EpharasEnlightenmentAbility3(state));
	}
}
