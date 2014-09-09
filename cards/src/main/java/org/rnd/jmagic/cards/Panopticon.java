package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Panopticon")
@Types({Type.PLANE})
@SubTypes({SubType.MIRRODIN})
@ColorIdentity({})
public final class Panopticon extends Card
{
	public static final class Draw extends EventTriggeredAbility
	{
		public Draw(GameState state)
		{
			super(state, "When you planeswalk to Panopticon, draw a card.");

			SimpleEventPattern pattern = new SimpleEventPattern(PlanechaseGameRules.PLANESWALK);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.TO, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			this.addEffect(drawACard());

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class DrawMore extends EventTriggeredAbility
	{
		public DrawMore(GameState state)
		{
			super(state, "At the beginning of your draw step, draw an additional card.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(Players.instance()));
			this.addPattern(pattern);

			this.addEffect(drawCards(You.instance(), 1, "Draw an additional card."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public static final class DrawEvenMore extends EventTriggeredAbility
	{
		public DrawEvenMore(GameState state)
		{
			super(state, "Whenever you roll (C), draw a card.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			this.addEffect(drawACard());

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Panopticon(GameState state)
	{
		super(state);

		this.addAbility(new Draw(state));

		this.addAbility(new DrawMore(state));

		this.addAbility(new DrawEvenMore(state));
	}
}
