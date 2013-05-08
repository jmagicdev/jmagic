package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Turntimber Grove")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TurntimberGrove extends Card
{
	public static final class ETBPump extends EventTriggeredAbility
	{
		public ETBPump(GameState state)
		{
			super(state, "When Turntimber Grove enters the battlefield, target creature gets +1/+1 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+1), (+1), "Target creature gets +1/+1 until end of turn."));
		}
	}

	public TurntimberGrove(GameState state)
	{
		super(state);

		// Turntimber Grove enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Turntimber Grove enters the battlefield, target creature gets
		// +1/+1 until end of turn.
		this.addAbility(new ETBPump(state));

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
