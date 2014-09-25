package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dismal Backwater")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DismalBackwater extends Card
{
	public static final class DismalBackwaterAbility1 extends EventTriggeredAbility
	{
		public DismalBackwaterAbility1(GameState state)
		{
			super(state, "When Dismal Backwater enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public DismalBackwater(GameState state)
	{
		super(state);

		// Dismal Backwater enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Dismal Backwater enters the battlefield, you gain 1 life.
		this.addAbility(new DismalBackwaterAbility1(state));

		// (T): Add (U) or (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(UB)"));
	}
}
