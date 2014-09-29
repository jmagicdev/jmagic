package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tranquil Cove")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class TranquilCove extends Card
{
	public static final class TranquilCoveAbility1 extends EventTriggeredAbility
	{
		public TranquilCoveAbility1(GameState state)
		{
			super(state, "When Tranquil Cove enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public static final class TranquilCoveAbility2 extends ActivatedAbility
	{
		public TranquilCoveAbility2(GameState state)
		{
			super(state, "(T): Add (W) or (U) to your mana pool.");
			this.costsTap = true;
		}
	}

	public TranquilCove(GameState state)
	{
		super(state);

		// Tranquil Cove enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Tranquil Cove enters the battlefield, you gain 1 life.
		this.addAbility(new TranquilCoveAbility1(state));

		// (T): Add (W) or (U) to your mana pool.
		this.addAbility(new TranquilCoveAbility2(state));
	}
}
