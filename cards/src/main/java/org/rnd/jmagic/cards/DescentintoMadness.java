package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Descent into Madness")
@Types({Type.ENCHANTMENT})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class DescentintoMadness extends Card
{
	public static final class DescentintoMadnessAbility0 extends EventTriggeredAbility
	{
		public DescentintoMadnessAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a despair counter on Descent into Madness, then each player exiles X permanents he or she controls and/or cards from his or her hand, where X is the number of despair counters on Descent into Madness.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(putCounters(1, Counter.CounterType.DESPAIR, ABILITY_SOURCE_OF_THIS, "Put a despair counter on Descent into Madness,"));

			DynamicEvaluation player = DynamicEvaluation.instance();

			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "each player exiles X permanents he or she controls and/or cards from his or her hand, where X is the number of despair counters on Descent into Madness.");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.NUMBER, Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.DESPAIR)));
			exile.parameters.put(EventType.Parameter.OBJECT, Union.instance(ControlledBy.instance(player), InZone.instance(HandOf.instance(player))));
			exile.parameters.put(EventType.Parameter.PLAYER, player);

			EventFactory foreach = new EventFactory(FOR_EACH_PLAYER, "then each player exiles X permanents he or she controls and/or cards from his or her hand, where X is the number of despair counters on Descent into Madness.");
			foreach.parameters.put(EventType.Parameter.TARGET, Identity.instance(player));
			foreach.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));
			this.addEffect(foreach);
		}
	}

	public DescentintoMadness(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, put a despair counter on Descent
		// into Madness, then each player exiles X permanents he or she controls
		// and/or cards from his or her hand, where X is the number of despair
		// counters on Descent into Madness.
		this.addAbility(new DescentintoMadnessAbility0(state));
	}
}
