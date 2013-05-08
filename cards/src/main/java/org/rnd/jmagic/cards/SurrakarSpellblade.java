package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Surrakar Spellblade")
@Types({Type.CREATURE})
@SubTypes({SubType.SURRAKAR})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SurrakarSpellblade extends Card
{
	public static final class SurrakarSpellbladeAbility0 extends EventTriggeredAbility
	{
		public SurrakarSpellbladeAbility0(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, you may put a charge counter on Surrakar Spellblade.");

			SimpleEventPattern youCastInstantOrSorcery = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			youCastInstantOrSorcery.put(EventType.Parameter.PLAYER, You.instance());
			youCastInstantOrSorcery.put(EventType.Parameter.OBJECT, HasType.instance(Type.INSTANT, Type.SORCERY));
			this.addPattern(youCastInstantOrSorcery);

			EventFactory counterFactory = putCountersOnThis(1, Counter.CounterType.CHARGE, "Surrakar Spellblade");
			EventFactory factory = youMay(counterFactory, "You may put a quest counter on Surrakar Spellblade.");
			this.addEffect(factory);
		}
	}

	public static final class SurrakarSpellbladeAbility1 extends EventTriggeredAbility
	{
		public SurrakarSpellbladeAbility1(GameState state)
		{
			super(state, "Whenever Surrakar Spellblade deals combat damage to a player, you may draw X cards, where X is the number of charge counters on it.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator numCounters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE));
			EventFactory draw = drawCards(You.instance(), numCounters, "Draw X cards, where X is the number of charge counters on Surrakar Spellblade.");
			this.addEffect(youMay(draw, "You may draw X cards, where X is the number of charge counters on Surrakar Spellblade."));
		}
	}

	public SurrakarSpellblade(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever you cast an instant or sorcery spell, you may put a charge
		// counter on Surrakar Spellblade.
		this.addAbility(new SurrakarSpellbladeAbility0(state));

		// Whenever Surrakar Spellblade deals combat damage to a player, you may
		// draw X cards, where X is the number of charge counters on it.
		this.addAbility(new SurrakarSpellbladeAbility1(state));
	}
}
