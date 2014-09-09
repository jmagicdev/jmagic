package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Hamletback Goliath")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GIANT})
@ManaCost("6R")
@ColorIdentity({Color.RED})
public final class HamletbackGoliath extends Card
{
	public static final class HamletbackGoliathAbility0 extends EventTriggeredAbility
	{
		public HamletbackGoliathAbility0(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield, you may put X +1/+1 counters on Hamletback Goliath, where X is that creature's power.");

			SetGenerator otherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), otherCreatures, false));

			SetGenerator X = PowerOf.instance(NewObjectOf.instance(TriggerZoneChange.instance(This.instance())));
			this.addEffect(youMay(putCounters(X, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put X +1/+1 counters on Hamletback Goliath, where X is that creature's power.")));
		}
	}

	public HamletbackGoliath(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Whenever another creature enters the battlefield, you may put X +1/+1
		// counters on Hamletback Goliath, where X is that creature's power.
		this.addAbility(new HamletbackGoliathAbility0(state));
	}
}
