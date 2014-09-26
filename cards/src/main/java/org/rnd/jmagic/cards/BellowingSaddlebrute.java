package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bellowing Saddlebrute")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ORC})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class BellowingSaddlebrute extends Card
{
	public static final class BellowingSaddlebruteAbility0 extends EventTriggeredAbility
	{
		public BellowingSaddlebruteAbility0(GameState state)
		{
			super(state, "Raid \u2014 When Bellowing Saddlebrute enters the battlefield, you lose 4 life unless you attacked with a creature this turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
			this.addEffect(ifElse(Raid.instance(), loseLife(You.instance(), 4, "You lose 4 life"), "You lose 4 life unless you attacked with a creature this turn."));
		}
	}

	public BellowingSaddlebrute(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Raid \u2014 When Bellowing Saddlebrute enters the battlefield, you
		// lose 4 life unless you attacked with a creature this turn.
		this.addAbility(new BellowingSaddlebruteAbility0(state));
	}
}
