package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Swiftwater Cliffs")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.BLUE})
public final class SwiftwaterCliffs extends Card
{
	public static final class SwiftwaterCliffsAbility1 extends EventTriggeredAbility
	{
		public SwiftwaterCliffsAbility1(GameState state)
		{
			super(state, "When Swiftwater Cliffs enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public SwiftwaterCliffs(GameState state)
	{
		super(state);

		// Swiftwater Cliffs enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Swiftwater Cliffs enters the battlefield, you gain 1 life.
		this.addAbility(new SwiftwaterCliffsAbility1(state));

		// (T): Add (U) or (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(UR)"));
	}
}
