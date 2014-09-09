package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Krosa")
@Types({Type.PLANE})
@SubTypes({SubType.DOMINARIA})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK, Color.RED, Color.GREEN})
public final class Krosa extends Card
{
	public static final class PumpOfKrosa extends StaticAbility
	{
		public PumpOfKrosa(GameState state)
		{
			super(state, "All creatures get +2/+2.");

			this.addEffectPart(modifyPowerAndToughness(CreaturePermanents.instance(), 2, 2));

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class ChaosMana extends EventTriggeredAbility
	{
		public ChaosMana(GameState state)
		{
			super(state, "Whenever you roll (C), you may add (W)(U)(B)(R)(G) to your mana pool.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			EventFactory mana = addManaToYourManaPoolFromAbility("(W)(U)(B)(R)(G)");
			this.addEffect(youMay(mana, "You may add (W)(U)(B)(R)(G) to your mana pool."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Krosa(GameState state)
	{
		super(state);

		this.addAbility(new PumpOfKrosa(state));

		this.addAbility(new ChaosMana(state));
	}
}
