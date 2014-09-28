package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Retribution of the Ancients")
@Types({Type.ENCHANTMENT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class RetributionoftheAncients extends Card
{
	public static final class RetributionoftheAncientsAbility0 extends ActivatedAbility
	{
		public RetributionoftheAncientsAbility0(GameState state)
		{
			super(state, "(B), Remove X +1/+1 counters from among creatures you control: Target creature gets -X/-X until end of turn.");
			this.setManaCost(new ManaPool("(B)"));

			SetGenerator X = ValueOfX.instance(This.instance());
			SetGenerator counters = CountersOn.instance(CREATURES_YOU_CONTROL, Counter.CounterType.PLUS_ONE_PLUS_ONE);

			EventFactory removeCounters = new EventFactory(EventType.REMOVE_COUNTERS_CHOICE, "Remove X +1/+1 counters from among creatures you control");
			removeCounters.parameters.put(EventType.Parameter.CAUSE, This.instance());
			removeCounters.parameters.put(EventType.Parameter.PLAYER, You.instance());
			removeCounters.parameters.put(EventType.Parameter.NUMBER, X);
			removeCounters.parameters.put(EventType.Parameter.COUNTER, counters);

		}
	}

	public RetributionoftheAncients(GameState state)
	{
		super(state);

		// (B), Remove X +1/+1 counters from among creatures you control: Target
		// creature gets -X/-X until end of turn.
		this.addAbility(new RetributionoftheAncientsAbility0(state));
	}
}
