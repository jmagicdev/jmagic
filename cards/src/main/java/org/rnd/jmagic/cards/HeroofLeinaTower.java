package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hero of Leina Tower")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class HeroofLeinaTower extends Card
{
	public static final class HeroofLeinaTowerAbility0 extends EventTriggeredAbility
	{
		public HeroofLeinaTowerAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Hero of Leina Tower, you may pay (X). If you do, put X +1/+1 counters on Hero of Leina Tower.");
			this.addPattern(heroic());

			EventFactory mayPayX = new EventFactory(EventType.PLAYER_MAY_PAY_X, "You may pay (X)(R)");
			mayPayX.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPayX.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mayPayX.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("R")));

			EventFactory pump = putCounters(EffectResult.instance(mayPayX), Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Hero of Leina Tower");

			this.addEffect(ifThen(mayPayX, pump, "You may pay (X). If you do, put X +1/+1 counters on Hero of Leina Tower."));
		}
	}

	public HeroofLeinaTower(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Heroic \u2014 Whenever you cast a spell that targets Hero of Leina
		// Tower, you may pay (X). If you do, put X +1/+1 counters on Hero of
		// Leina Tower.
		this.addAbility(new HeroofLeinaTowerAbility0(state));
	}
}
