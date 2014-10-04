package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Frenzied Goblin")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.GOBLIN})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class FrenziedGoblin extends Card
{
	public static final class FrenziedGoblinAbility0 extends EventTriggeredAbility
	{
		public FrenziedGoblinAbility0(GameState state)
		{
			super(state, "Whenever Frenzied Goblin attacks, you may pay (R). If you do, target creature can't block this turn.");
			this.addPattern(whenThisAttacks());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			EventFactory stun = cantBlockThisTurn(target, "Target creature can't block this turn.");

			this.addEffect(ifThen(youMayPay("(R)"), stun, "You may pay (R). If you do, target creature can't block this turn."));
		}
	}

	public FrenziedGoblin(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Frenzied Goblin attacks, you may pay (R). If you do, target
		// creature can't block this turn.
		this.addAbility(new FrenziedGoblinAbility0(state));
	}
}
