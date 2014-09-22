package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blossoming Sands")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class BlossomingSands extends Card
{
	public static final class BlossomingSandsAbility1 extends EventTriggeredAbility
	{
		public BlossomingSandsAbility1(GameState state)
		{
			super(state, "When Blossoming Sands enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public BlossomingSands(GameState state)
	{
		super(state);

		// Blossoming Sands enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Blossoming Sands enters the battlefield, you gain 1 life.
		this.addAbility(new BlossomingSandsAbility1(state));

		// (T): Add (G) or (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GW)"));
	}
}
