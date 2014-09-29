package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thornwood Falls")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class ThornwoodFalls extends Card
{
	public static final class ThornwoodFallsAbility1 extends EventTriggeredAbility
	{
		public ThornwoodFallsAbility1(GameState state)
		{
			super(state, "When Thornwood Falls enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public ThornwoodFalls(GameState state)
	{
		super(state);

		// Thornwood Falls enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Thornwood Falls enters the battlefield, you gain 1 life.
		this.addAbility(new ThornwoodFallsAbility1(state));

		// (T): Add (G) or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GU)"));
	}
}
