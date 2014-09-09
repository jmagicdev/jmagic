package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Azor's Elocutors")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ADVISOR})
@ManaCost("3(W/U)(W/U)")
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AzorsElocutors extends Card
{
	public static final class AzorsElocutorsAbility0 extends EventTriggeredAbility
	{
		public AzorsElocutorsAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a filibuster counter on Azor's Elocutors. Then if Azor's Elocutors has five or more filibuster counters on it, you win the game.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			this.addEffect(putCounters(1, Counter.CounterType.FILIBUSTER, ABILITY_SOURCE_OF_THIS, "Put a filibuster counter on Azor's Elocutors."));

			this.addEffect(ifThen(Intersect.instance(Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.FILIBUSTER)), Between.instance(5, null)), youWinTheGame(), "Then if Azor's Elocutors has five or more filibuster counters on it, you win the game."));
		}
	}

	public static final class AzorsElocutorsAbility1 extends EventTriggeredAbility
	{
		public AzorsElocutorsAbility1(GameState state)
		{
			super(state, "Whenever a source deals damage to you, remove a filibuster counter from Azor's Elocutors.");
			this.addPattern(new SimpleDamagePattern(null, You.instance()));
			this.addEffect(removeCounters(1, Counter.CounterType.FILIBUSTER, ABILITY_SOURCE_OF_THIS, "Remove a filibuster counter from Azor's Elocutors."));
		}
	}

	public AzorsElocutors(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// At the beginning of your upkeep, put a filibuster counter on Azor's
		// Elocutors. Then if Azor's Elocutors has five or more filibuster
		// counters on it, you win the game.
		this.addAbility(new AzorsElocutorsAbility0(state));

		// Whenever a source deals damage to you, remove a filibuster counter
		// from Azor's Elocutors.
		this.addAbility(new AzorsElocutorsAbility1(state));
	}
}
