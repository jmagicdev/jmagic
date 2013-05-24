package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Eternity Vessel")
@Types({Type.ARTIFACT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class EternityVessel extends Card
{
	public static final class QuickLoad extends EventTriggeredAbility
	{
		public QuickLoad(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may have your life total become the number of charge counters on Eternity Vessel.");

			this.addPattern(landfall());

			EventFactory lifeFactory = new EventFactory(EventType.SET_LIFE, "Your life total becomes the number of charge counters on Eternity Vessel");
			lifeFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			lifeFactory.parameters.put(EventType.Parameter.NUMBER, Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE)));
			lifeFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(youMay(lifeFactory, "You may have your life total becomes the number of charge counters on Eternity Vessel."));
		}
	}

	public EternityVessel(GameState state)
	{
		super(state);

		// Eternity Vessel enters the battlefield with X charge counters on it,
		// where X is your life total.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Eternity Vessel", LifeTotalOf.instance(You.instance()), "X charge counters on it, where X is your life total", Counter.CounterType.CHARGE));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may have your life total become the number of charge
		// counters on Eternity Vessel.
		this.addAbility(new QuickLoad(state));
	}
}
