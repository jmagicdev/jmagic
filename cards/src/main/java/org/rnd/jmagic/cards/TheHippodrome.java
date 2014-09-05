package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("The Hippodrome")
@Types({Type.PLANE})
@SubTypes({SubType.SEGOVIA})
@Printings({@Printings.Printed(ex = org.rnd.jmagic.expansions.Planechase.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class TheHippodrome extends Card
{
	public static final class DoranSaysWhat extends StaticAbility
	{
		public DoranSaysWhat(GameState state)
		{
			super(state, "All creatures get -5/-0.");

			this.addEffectPart(modifyPowerAndToughness(CreaturePermanents.instance(), -5, 0));

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class Darwinism extends EventTriggeredAbility
	{
		public Darwinism(GameState state)
		{
			super(state, "Whenever you roll (C), you may destroy target creature if its power is 0 or less.");

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			EventFactory factory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "You may destroy target creature if its power is 0 or less.");
			factory.parameters.put(EventType.Parameter.IF, Intersect.instance(targetedBy(target), HasPower.instance(Between.instance(null, 0))));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(youMay(destroy(targetedBy(target), "Destroy target creature."), "You may destroy target creature.")));
			this.addEffect(factory);

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public TheHippodrome(GameState state)
	{
		super(state);

		this.addAbility(new DoranSaysWhat(state));

		this.addAbility(new Darwinism(state));
	}
}
