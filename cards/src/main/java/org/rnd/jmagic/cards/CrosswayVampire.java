package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crossway Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1RR")
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
			this.addEffect(cantBlockThisTurn(target, "Target creature can't block this turn"));
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
