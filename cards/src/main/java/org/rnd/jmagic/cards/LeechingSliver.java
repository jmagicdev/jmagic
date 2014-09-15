package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Leeching Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class LeechingSliver extends Card
{
	public static final class LeechingSliverAbility0 extends EventTriggeredAbility
	{
		public LeechingSliverAbility0(GameState state)
		{
			super(state, "Whenever a Sliver you control attacks, defending player loses 1 life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, SLIVER_CREATURES_YOU_CONTROL);

			SetGenerator defender = DefendingPlayer.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT));
			this.addEffect(loseLife(defender, 1, "Defending player loses 1 life."));
		}
	}

	public LeechingSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever a Sliver you control attacks, defending player loses 1 life.
		this.addAbility(new LeechingSliverAbility0(state));
	}
}
