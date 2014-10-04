package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Smoldering Spires")
@Types({Type.LAND})
@ColorIdentity({Color.RED})
public final class SmolderingSpires extends Card
{
	public static final class Stun extends EventTriggeredAbility
	{
		public Stun(GameState state)
		{
			super(state, "When Smoldering Spires enters the battlefield, target creature can't block this turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetedBy(target))));

			this.addEffect(cantBlockThisTurn(targetedBy(target), "Target creature can't block this turn."));
		}
	}

	public SmolderingSpires(GameState state)
	{
		super(state);

		// Smoldering Spires enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Smoldering Spires enters the battlefield, target creature can't
		// block this turn.
		this.addAbility(new Stun(state));

		// (T): Add (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForR(state));
	}
}
