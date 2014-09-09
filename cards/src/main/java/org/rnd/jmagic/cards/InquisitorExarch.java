package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Inquisitor Exarch")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class InquisitorExarch extends Card
{
	public static final class InquisitorExarchAbility0 extends EventTriggeredAbility
	{
		public InquisitorExarchAbility0(GameState state)
		{
			super(state, "When Inquisitor Exarch enters the battlefield, choose one \u2014\n\u2022 You gain 2 life.\n\u2022 Target opponent loses 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(1, gainLife(You.instance(), 2, "You gain 2 life"));
			this.addEffect(2, loseLife(targetedBy(this.addTarget(2, OpponentsOf.instance(You.instance()), "target opponent")), 2, "target opponent loses 2 life"));
		}
	}

	public InquisitorExarch(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Inquisitor Exarch enters the battlefield, choose one \u2014 You
		// gain 2 life; or target opponent loses 2 life.
		this.addAbility(new InquisitorExarchAbility0(state));
	}
}
