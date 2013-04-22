package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Shortcutter")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.GOBLIN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinShortcutter extends Card
{
	public static final class Shortcut extends EventTriggeredAbility
	{
		public Shortcut(GameState state)
		{
			super(state, "When Goblin Shortcutter enters the battlefield, target creature can't block this turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetedBy(target))));

			this.addEffect(createFloatingEffect("Target creature can't block this turn.", part));
		}
	}

	public GoblinShortcutter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Goblin Shortcutter enters the battlefield, target creature can't
		// block this turn.
		this.addAbility(new Shortcut(state));
	}
}
