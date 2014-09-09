package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Bant")
@Types({Type.PLANE})
@SubTypes({SubType.ALARA})
@ColorIdentity({})
public final class Bant extends Card
{
	public static final class ExaltedForAll extends StaticAbility
	{
		public ExaltedForAll(GameState state)
		{
			super(state, "All creatures have exalted.");

			this.addEffectPart(addAbilityToObject(CreaturePermanents.instance(), org.rnd.jmagic.abilities.keywords.Exalted.class));

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class IndestructibleChaos extends EventTriggeredAbility
	{
		public IndestructibleChaos(GameState state)
		{
			super(state, "Whenever you roll (C), put a divinity counter on target green, white, or blue creature. That creature has indestructible as long as it has a divinity counter on it.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.GREEN, Color.WHITE, Color.BLUE)), "target green, white, or blue creature");
			this.addEffect(putCounters(1, Counter.CounterType.DIVINITY, targetedBy(target), "Put a divinity counter on target green, white, or blue creature."));
			this.addEffect(createFloatingEffect(CountersOn.instance(targetedBy(target), Counter.CounterType.DIVINITY), "That creature has indestructible as long as it has a divinity counter on it.", addAbilityToObject(targetedBy(target), org.rnd.jmagic.abilities.keywords.Indestructible.class)));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Bant(GameState state)
	{
		super(state);

		this.addAbility(new ExaltedForAll(state));

		this.addAbility(new IndestructibleChaos(state));
	}
}
