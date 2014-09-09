package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ob Nixilis, the Fallen")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class ObNixilistheFallen extends Card
{
	public static final class LandfallIsRidiculous extends EventTriggeredAbility
	{
		public LandfallIsRidiculous(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may have target player lose 3 life. If you do, put three +1/+1 counters on Ob Nixilis, the Fallen.");
			this.addPattern(landfall());

			Target target = this.addTarget(Players.instance(), "target player");

			EventFactory loseLife = loseLife(targetedBy(target), 3, "Target player loses 3 life");
			EventFactory mayCauseLifeLoss = youMay(loseLife, "You may have target player lose 3 life");
			EventFactory putCounters = putCountersOnThis(3, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Ob Nixilis, the Fallen");

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may have target player lose 3 life. If you do, put three +1/+1 counters on Ob Nixilis, the Fallen.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayCauseLifeLoss));
			effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(putCounters));
			this.addEffect(effect);
		}
	}

	public ObNixilistheFallen(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may have target player lose 3 life. If you do, put three
		// +1/+1 counters on Ob Nixilis, the Fallen.
		this.addAbility(new LandfallIsRidiculous(state));
	}
}
