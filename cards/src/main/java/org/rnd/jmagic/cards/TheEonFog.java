package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;
import org.rnd.jmagic.gameTypes.*;

@Name("The Eon Fog")
@Types({Type.PLANE})
@SubTypes({SubType.EQUILOR})
@ColorIdentity({})
public final class TheEonFog extends Card
{
	public static final class SkipUntap extends StaticAbility
	{
		public SkipUntap(GameState state)
		{
			super(state, "Players skip their untap steps.");

			SimpleEventPattern untaps = new SimpleEventPattern(EventType.BEGIN_STEP);
			untaps.put(EventType.Parameter.STEP, UntapStepOf.instance(Players.instance()));

			EventReplacementEffect skipUntaps = new EventReplacementEffect(this.game, "Players skip their untap steps", untaps);

			this.addEffectPart(replacementEffectPart(skipUntaps));

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class Untap extends EventTriggeredAbility
	{
		public Untap(GameState state)
		{
			super(state, "Whenever you roll (C), untap all permanents you control.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			this.addEffect(untap(ControlledBy.instance(You.instance()), "Untap all permanents you control."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public TheEonFog(GameState state)
	{
		super(state);

		this.addAbility(new SkipUntap(state));

		this.addAbility(new Untap(state));
	}
}
