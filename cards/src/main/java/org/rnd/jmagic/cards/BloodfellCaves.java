package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloodfell Caves")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.RED})
public final class BloodfellCaves extends Card
{
	public static final class BloodfellCavesAbility1 extends EventTriggeredAbility
	{
		public BloodfellCavesAbility1(GameState state)
		{
			super(state, "When Bloodfell Caves enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public BloodfellCaves(GameState state)
	{
		super(state);

		// Bloodfell Caves enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Bloodfell Caves enters the battlefield, you gain 1 life.
		this.addAbility(new BloodfellCavesAbility1(state));

		// (T): Add (B) or (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BR)"));
	}
}
