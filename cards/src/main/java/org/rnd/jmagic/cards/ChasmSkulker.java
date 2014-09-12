package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Chasm Skulker")
@Types({Type.CREATURE})
@SubTypes({SubType.SQUID, SubType.HORROR})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class ChasmSkulker extends Card
{
	public static final class ChasmSkulkerAbility0 extends EventTriggeredAbility
	{
		public ChasmSkulkerAbility0(GameState state)
		{
			super(state, "Whenever you draw a card, put a +1/+1 counter on Chasm Skulker.");

			SimpleEventPattern draw = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			draw.put(EventType.Parameter.PLAYER, You.instance());
			this.addPattern(draw);

			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Chasm Skulker"));
		}
	}

	public static final class ChasmSkulkerAbility1 extends EventTriggeredAbility
	{
		public ChasmSkulkerAbility1(GameState state)
		{
			super(state, "When Chasm Skulker dies, put X 1/1 blue Squid creature tokens with islandwalk onto the battlefield, where X is the number of +1/+1 counters on Chasm Skulker.");
			this.addPattern(whenThisDies());

			SetGenerator X = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.PLUS_ONE_PLUS_ONE));
			CreateTokensFactory squid = new CreateTokensFactory(X, numberGenerator(1), numberGenerator(1), "Put X 1/1 blue Squid creature tokens with islandwalk onto the battlefield, where X is the number of +1/+1 counters on Chasm Skulker.");
			squid.setColors(Color.BLUE);
			squid.setSubTypes(SubType.SQUID);
			squid.addAbility(org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk.class);
			this.addEffect(squid.getEventFactory());
		}
	}

	public ChasmSkulker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever you draw a card, put a +1/+1 counter on Chasm Skulker.
		this.addAbility(new ChasmSkulkerAbility0(state));

		// When Chasm Skulker dies, put X 1/1 blue Squid creature tokens with
		// islandwalk onto the battlefield, where X is the number of +1/+1
		// counters on Chasm Skulker. (They can't be blocked as long as
		// defending player controls an Island.)
		this.addAbility(new ChasmSkulkerAbility1(state));
	}
}
