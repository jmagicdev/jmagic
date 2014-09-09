package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soul Conduit")
@Types({Type.ARTIFACT})
@ManaCost("6")
@ColorIdentity({})
public final class SoulConduit extends Card
{
	public static final class SoulConduitAbility0 extends ActivatedAbility
	{
		public SoulConduitAbility0(GameState state)
		{
			super(state, "(6), (T): Two target players exchange life totals.");
			this.setManaCost(new ManaPool("(6)"));
			this.costsTap = true;

			Target targets = this.addTarget(Players.instance(), "two target players");
			targets.setNumber(2, 2);

			EventFactory exchange = new EventFactory(EventType.EXCHANGE_LIFE_TOTALS, "Two target players exchange life totals.");
			exchange.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exchange.parameters.put(EventType.Parameter.PLAYER, targetedBy(targets));
			this.addEffect(exchange);
		}
	}

	public SoulConduit(GameState state)
	{
		super(state);

		// (6), (T): Two target players exchange life totals.
		this.addAbility(new SoulConduitAbility0(state));
	}
}
