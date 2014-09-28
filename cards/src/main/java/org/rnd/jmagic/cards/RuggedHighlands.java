package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rugged Highlands")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.GREEN})
public final class RuggedHighlands extends Card
{
	public static final class RuggedHighlandsAbility1 extends EventTriggeredAbility
	{
		public RuggedHighlandsAbility1(GameState state)
		{
			super(state, "When Rugged Highlands enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public RuggedHighlands(GameState state)
	{
		super(state);

		// Rugged Highlands enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Rugged Highlands enters the battlefield, you gain 1 life.
		this.addAbility(new RuggedHighlandsAbility1(state));

		// (T): Add (R) or (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(RG)"));
	}
}
