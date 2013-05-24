package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Transguild Promenade")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({})
public final class TransguildPromenade extends Card
{
	public static final class TransguildPromenadeAbility1 extends EventTriggeredAbility
	{
		public TransguildPromenadeAbility1(GameState state)
		{
			super(state, "When Transguild Promenade enters the battlefield, sacrifice it unless you pay (1).");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory sacrifice = sacrificeThis("Transguild Promenade");

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (1)");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("(1)")));
			pay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(unless(You.instance(), sacrifice, pay, "Sacrifice Transguild Promenade unless you pay (1)."));
		}
	}

	public TransguildPromenade(GameState state)
	{
		super(state);

		// Transguild Promenade enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Transguild Promenade enters the battlefield, sacrifice it unless
		// you pay (1).
		this.addAbility(new TransguildPromenadeAbility1(state));

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
