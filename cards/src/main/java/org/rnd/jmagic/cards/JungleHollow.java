package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jungle Hollow")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class JungleHollow extends Card
{
	public static final class JungleHollowAbility1 extends EventTriggeredAbility
	{
		public JungleHollowAbility1(GameState state)
		{
			super(state, "When Jungle Hollow enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public JungleHollow(GameState state)
	{
		super(state);

		// Jungle Hollow enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Jungle Hollow enters the battlefield, you gain 1 life.
		this.addAbility(new JungleHollowAbility1(state));

		// (T): Add (B) or (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BG)"));
	}
}
