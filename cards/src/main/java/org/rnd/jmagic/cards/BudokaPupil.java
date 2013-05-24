package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Budoka Pupil")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MONK})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.BETRAYERS_OF_KAMIGAWA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
@BottomHalf(IchigaWhoTopplesOaks.class)
public final class BudokaPupil extends Card
{
	public BudokaPupil(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new GainKi(state));

		this.addAbility(new FlipMe(state));
	}

	public static final class GainKi extends EventTriggeredAbility
	{
		public GainKi(GameState state)
		{
			super(state, "Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Budoka Pupil.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasSubType.instance(SubType.SPIRIT, SubType.ARCANE)));
			this.addPattern(pattern);

			this.addEffect(youMay(putCountersOnThis(1, Counter.CounterType.KI, "Budoka Pupil"), "You may put a ki counter on Budoka Pupil."));
		}
	}

	public static final class FlipMe extends EventTriggeredAbility
	{
		public FlipMe(GameState state)
		{
			super(state, "At the beginning of the end step, if there are two or more ki counters on Budoka Pupil, you may flip it.");

			this.addPattern(atTheBeginningOfTheEndStep());

			this.interveningIf = Intersect.instance(Between.instance(2, null), Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.KI)));

			EventFactory flipThis = new EventFactory(EventType.FLIP_CARD, "Flip it.");
			flipThis.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);

			this.addEffect(youMay(flipThis, "You may flip it."));
		}
	}
}
