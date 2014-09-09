package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Cunning Bandit")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
@BottomHalf(AzamukiTreacheryIncarnate.class)
public final class CunningBandit extends Card
{
	public static final class GainKi extends EventTriggeredAbility
	{
		public GainKi(GameState state)
		{
			super(state, "Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Cunning Bandit.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasSubType.instance(SubType.SPIRIT, SubType.ARCANE)));
			this.addPattern(pattern);

			this.addEffect(youMay(putCountersOnThis(1, Counter.CounterType.KI, "Cunning Bandit"), "You may put a ki counter on Cunning Bandit."));
		}
	}

	public static final class FlipMe extends EventTriggeredAbility
	{
		public FlipMe(GameState state)
		{
			super(state, "At the beginning of the end step, if there are two or more ki counters on Cunning Bandit, you may flip it.");

			this.addPattern(atTheBeginningOfTheEndStep());

			this.interveningIf = Intersect.instance(Between.instance(2, null), Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.KI)));

			EventFactory flipThis = new EventFactory(EventType.FLIP_CARD, "Flip it.");
			flipThis.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);

			this.addEffect(youMay(flipThis, "You may flip it."));
		}
	}

	public CunningBandit(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever you cast a Spirit or Arcane spell, you may put a ki counter
		// on Cunning Bandit.
		this.addAbility(new GainKi(state));

		// At the beginning of the end step, if there are two or more ki
		// counters on Cunning Bandit, you may flip it.
		this.addAbility(new FlipMe(state));
	}
}
