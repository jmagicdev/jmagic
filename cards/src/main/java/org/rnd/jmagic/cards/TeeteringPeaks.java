package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Teetering Peaks")
@Types({Type.LAND})
@ColorIdentity({Color.RED})
public final class TeeteringPeaks extends Card
{
	public static final class ETBPump extends EventTriggeredAbility
	{
		public ETBPump(GameState state)
		{
			super(state, "When Teetering Peaks enters the battlefield, target creature gets +2/+0 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+2), (+0), "Target creature gets +2/+0 until end of turn."));
		}
	}

	public TeeteringPeaks(GameState state)
	{
		super(state);

		// Teetering Peaks enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Teetering Peaks enters the battlefield, target creature gets
		// +2/+0 until end of turn.
		this.addAbility(new ETBPump(state));

		// {T}: Add {R} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForR(state));
	}
}
