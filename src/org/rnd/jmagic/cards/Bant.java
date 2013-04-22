package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bant")
@Types({Type.PLANE})
@SubTypes({SubType.ALARA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Bant extends Card
{
	public static final class ExaltedForAll extends StaticAbility
	{
		public ExaltedForAll(GameState state)
		{
			super(state, "All creatures have exalted.");

			this.addEffectPart(addAbilityToObject(CreaturePermanents.instance(), org.rnd.jmagic.abilities.keywords.Exalted.class));

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class IndestructibleChaos extends EventTriggeredAbility
	{
		public IndestructibleChaos(GameState state)
		{
			super(state, "Whenever you roll (C), put a divinity counter on target green, white, or blue creature. That creature is indestructible as long as it has a divinity counter on it.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasColor.instance(Color.GREEN, Color.WHITE, Color.BLUE)), "target green, white, or blue creature");
			this.addEffect(putCounters(1, Counter.CounterType.DIVINITY, targetedBy(target), "Put a divinity counter on target green, white, or blue creature."));
			this.addEffect(createFloatingEffect(CountersOn.instance(targetedBy(target), Counter.CounterType.DIVINITY), "That creature is indestructible as long as it has a divinity counter on it.", indestructible(targetedBy(target))));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public Bant(GameState state)
	{
		super(state);

		this.addAbility(new ExaltedForAll(state));

		this.addAbility(new IndestructibleChaos(state));
	}
}
