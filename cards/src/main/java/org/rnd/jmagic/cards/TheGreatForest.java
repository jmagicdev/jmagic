package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;

@Name("The Great Forest")
@Types({Type.PLANE})
@SubTypes({SubType.LORWYN})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class TheGreatForest extends Card
{
	public static final class HelloDoran extends StaticAbility
	{
		public HelloDoran(GameState state)
		{
			super(state, "Each creature assigns combat damage equal to its toughness rather than its power.");

			this.addEffectPart(new ContinuousEffect.Part(ContinuousEffectType.ASSIGN_COMBAT_DAMAGE_USING_TOUGHNESS));

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class ItsBarkIsWorseThanItsBite extends EventTriggeredAbility
	{
		public ItsBarkIsWorseThanItsBite(GameState state)
		{
			super(state, "Whenever you roll (C), creatures you control get +0/+2 and gain trample until end of turn.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			this.addEffect(createFloatingEffect("Creatures you control get +0/+2 and gain trample until end of turn.", modifyPowerAndToughness(CREATURES_YOU_CONTROL, 0, 2), addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Trample.class)));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public TheGreatForest(GameState state)
	{
		super(state);

		this.addAbility(new HelloDoran(state));

		this.addAbility(new ItsBarkIsWorseThanItsBite(state));
	}
}
