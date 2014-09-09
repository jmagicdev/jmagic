package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thought Gorger")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class ThoughtGorger extends Card
{
	public static final class ThoughtGorgerAbility1 extends EventTriggeredAbility
	{
		public ThoughtGorgerAbility1(GameState state)
		{
			super(state, "When Thought Gorger enters the battlefield, put a +1/+1 counter on it for each card in your hand. If you do, discard your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator number = Count.instance(InZone.instance(HandOf.instance(You.instance())));
			EventFactory counters = putCounters(number, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Thought Gorger for each card in your hand");
			EventFactory discard = discardHand(You.instance(), "Discard your hand");

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Put a +1/+1 counter on Thought Gorger for each card in your hand. If you do, discard your hand.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(counters));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(discard));
			this.addEffect(effect);
		}
	}

	public static final class ThoughtGorgerAbility2 extends EventTriggeredAbility
	{
		public ThoughtGorgerAbility2(GameState state)
		{
			super(state, "When Thought Gorger leaves the battlefield, draw a card for each +1/+1 counter on it.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator number = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffect(drawCards(You.instance(), number, "Draw a card for each +1/+1 counter on Thought Gorger."));
		}
	}

	public ThoughtGorger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// When Thought Gorger enters the battlefield, put a +1/+1 counter on it
		// for each card in your hand. If you do, discard your hand.
		this.addAbility(new ThoughtGorgerAbility1(state));

		// When Thought Gorger leaves the battlefield, draw a card for each
		// +1/+1 counter on it.
		this.addAbility(new ThoughtGorgerAbility2(state));
	}
}
