package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Archmage Ascension")
@Types({Type.ENCHANTMENT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class ArchmageAscension extends Card
{
	public static final class BrainQuest extends EventTriggeredAbility
	{
		public BrainQuest(GameState state)
		{
			super(state, "At the beginning of each end step, if you drew two or more cards this turn, you may put a quest counter on Archmage Ascension.");

			this.addPattern(atTheBeginningOfTheEndStep());

			state.ensureTracker(new NumberCardsDrawnBy.DrawCounter());
			this.interveningIf = Intersect.instance(NumberCardsDrawnBy.instance(You.instance()), Between.instance(2, null));

			this.addEffect(youMayPutAQuestCounterOnThis("Archmage Ascension"));
		}
	}

	public static final class DrawTutor extends StaticAbility
	{
		public DrawTutor(GameState state)
		{
			super(state, "As long as Archmage Ascension has six or more quest counters on it, if you would draw a card, you may instead search your library for a card, put that card into your hand, then shuffle your library.");

			this.canApply = Both.instance(this.canApply, Intersect.instance(Count.instance(CountersOn.instance(This.instance(), Counter.CounterType.QUEST)), Between.instance(6, null)));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			pattern.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a card, put that card into your hand, then shuffle your library.");
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));

			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If you would draw a card, you may instead search your library for a card, put that card into your hand, then shuffle your library", pattern);
			replacement.makeOptional(You.instance());
			replacement.addEffect(factory);
			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public ArchmageAscension(GameState state)
	{
		super(state);

		this.addAbility(new BrainQuest(state));

		this.addAbility(new DrawTutor(state));
	}
}
