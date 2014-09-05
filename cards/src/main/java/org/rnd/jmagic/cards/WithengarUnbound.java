package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Withengar Unbound")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class WithengarUnbound extends AlternateCard
{
	public static final class WithengarUnboundAbility1 extends EventTriggeredAbility
	{
		public WithengarUnboundAbility1(GameState state)
		{
			super(state, "Whenever a player loses the game, put thirteen +1/+1 counters on Withengar Unbound.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.LOSE_GAME);
			pattern.put(EventType.Parameter.PLAYER, Players.instance());
			this.addPattern(pattern);

			this.addEffect(putCounters(13, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put thirteen +1/+1 counters on Withengar Unbound."));
		}
	}

	public WithengarUnbound(GameState state)
	{
		super(state);

		this.setPower(13);
		this.setToughness(13);

		this.setColorIndicator(Color.BLACK);

		// Flying, intimidate, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever a player loses the game, put thirteen +1/+1 counters on
		// Withengar Unbound.
		this.addAbility(new WithengarUnboundAbility1(state));
	}
}
