package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Cosi's Trickster")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class CosisTrickster extends Card
{
	public static final class ShufflePump extends EventTriggeredAbility
	{
		public ShufflePump(GameState state)
		{
			super(state, "Whenever an opponent shuffles his or her library, you may put a +1/+1 counter on Cosi's Trickster.");

			SimpleEventPattern opponentShuffles = new SimpleEventPattern(EventType.SHUFFLE_ONE_LIBRARY);
			opponentShuffles.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			this.addPattern(opponentShuffles);

			this.addEffect(youMay(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Cosi's Trickster"), "You may put a +1/+1 counter on Cosi's Trickster."));
		}
	}

	public CosisTrickster(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever an opponent shuffles his or her library, you may put a +1/+1
		// counter on Cosi's Trickster.
		this.addAbility(new ShufflePump(state));
	}
}
