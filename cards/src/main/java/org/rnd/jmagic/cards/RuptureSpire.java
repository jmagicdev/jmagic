package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rupture Spire")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.COMMON)})
@ColorIdentity({})
public final class RuptureSpire extends Card
{
	public static final class Rupturing extends EventTriggeredAbility
	{
		public Rupturing(GameState state)
		{
			super(state, "When Rupture Spire enters the battlefield, sacrifice it unless you pay (1).");

			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory sacrifice = sacrificeThis("Rupture Spire");

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (1)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
			pay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(unless(You.instance(), sacrifice, pay, "Sacrifice Rupture Spire unless you pay (1)."));
		}
	}

	public RuptureSpire(GameState state)
	{
		super(state);

		// Rupture Spire enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Rupture Spire enters the battlefield, sacrifice it unless you
		// pay (1).
		this.addAbility(new Rupturing(state));

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
