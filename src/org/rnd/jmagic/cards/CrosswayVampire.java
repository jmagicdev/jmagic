package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crossway Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class CrosswayVampire extends Card
{
	public static final class CrosswayVampireAbility0 extends EventTriggeredAbility
	{
		public CrosswayVampireAbility0(GameState state)
		{
			super(state, "When Crossway Vampire enters the battlefield, target creature can't block this turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(target, Blocking.instance())));
			this.addEffect(createFloatingEffect("Target creature can't block this turn", part));
		}
	}

	public CrosswayVampire(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// When Crossway Vampire enters the battlefield, target creature can't
		// block this turn.
		this.addAbility(new CrosswayVampireAbility0(state));
	}
}
