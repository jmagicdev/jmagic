package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Grotag Thrasher")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GrotagThrasher extends Card
{
	public static final class GrotagThrasherAbility0 extends EventTriggeredAbility
	{
		public GrotagThrasherAbility0(GameState state)
		{
			super(state, "Whenever Grotag Thrasher attacks, target creature can't block this turn.");
			this.addPattern(whenThisAttacks());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetedBy(target))));

			this.addEffect(createFloatingEffect("Target creature can't block this turn.", part));
		}
	}

	public GrotagThrasher(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever Grotag Thrasher attacks, target creature can't block this
		// turn.
		this.addAbility(new GrotagThrasherAbility0(state));
	}
}
