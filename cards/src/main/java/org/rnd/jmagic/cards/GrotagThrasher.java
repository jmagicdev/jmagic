package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grotag Thrasher")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("4R")
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

			this.addEffect(cantBlockThisTurn(targetedBy(target), "Target creature can't block this turn."));
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
