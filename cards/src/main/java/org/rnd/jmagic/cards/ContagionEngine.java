package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Contagion Engine")
@Types({Type.ARTIFACT})
@ManaCost("6")
@ColorIdentity({})
public final class ContagionEngine extends Card
{
	public static final class ContagionEngineAbility0 extends EventTriggeredAbility
	{
		public ContagionEngineAbility0(GameState state)
		{
			super(state, "When Contagion Engine enters the battlefield, put a -1/-1 counter on each creature target player controls.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(target)), "Put a -1/-1 counter on each creature target player controls."));
		}
	}

	public static final class ContagionEngineAbility1 extends ActivatedAbility
	{
		public ContagionEngineAbility1(GameState state)
		{
			super(state, "(4), (T): Proliferate, then proliferate again.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;
			this.addEffect(proliferate());
			this.addEffect(proliferate());
		}
	}

	public ContagionEngine(GameState state)
	{
		super(state);

		// When Contagion Engine enters the battlefield, put a -1/-1 counter on
		// each creature target player controls.
		this.addAbility(new ContagionEngineAbility0(state));

		// (4), (T): Proliferate, then proliferate again. (You choose any number
		// of permanents and/or players with counters on them, then give each
		// another counter of a kind already there. Then do it again.)
		this.addAbility(new ContagionEngineAbility1(state));
	}
}
