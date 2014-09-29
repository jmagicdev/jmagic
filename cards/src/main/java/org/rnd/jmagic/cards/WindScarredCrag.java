package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wind-Scarred Crag")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.WHITE})
public final class WindScarredCrag extends Card
{
	public static final class WindScarredCragAbility1 extends EventTriggeredAbility
	{
		public WindScarredCragAbility1(GameState state)
		{
			super(state, "When Wind-Scarred Crag enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public static final class WindScarredCragAbility2 extends ActivatedAbility
	{
		public WindScarredCragAbility2(GameState state)
		{
			super(state, "(T): Add (R) or (W) to your mana pool.");
			this.costsTap = true;
		}
	}

	public WindScarredCrag(GameState state)
	{
		super(state);

		// Wind-Scarred Crag enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Wind-Scarred Crag enters the battlefield, you gain 1 life.
		this.addAbility(new WindScarredCragAbility1(state));

		// (T): Add (R) or (W) to your mana pool.
		this.addAbility(new WindScarredCragAbility2(state));
	}
}
