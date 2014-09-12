package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Feast on the Fallen")
@Types({Type.ENCHANTMENT})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class FeastontheFallen extends Card
{
	public static final class FeastontheFallenAbility0 extends EventTriggeredAbility
	{
		public FeastontheFallenAbility0(GameState state)
		{
			super(state, "At the beginning of each upkeep, if an opponent lost life last turn, put a +1/+1 counter on target creature you control.");
			this.addPattern(atTheBeginningOfEachUpkeep());

			state.ensureTracker(new LostLifeLastTurn.Tracker());
			this.interveningIf = Intersect.instance(LostLifeLastTurn.instance(), OpponentsOf.instance(You.instance()));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature you control."));
		}
	}

	public FeastontheFallen(GameState state)
	{
		super(state);

		// At the beginning of each upkeep, if an opponent lost life last turn,
		// put a +1/+1 counter on target creature you control.
		this.addAbility(new FeastontheFallenAbility0(state));
	}
}
