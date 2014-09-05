package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Temple of Plenty")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.BORN_OF_THE_GODS, r = Rarity.RARE)})
@ColorIdentity({})
public final class TempleofPlenty extends Card
{
	public static final class TempleofPlentyAbility1 extends EventTriggeredAbility
	{
		public TempleofPlentyAbility1(GameState state)
		{
			super(state, "When Temple of Plenty enters the battlefield, scry 1.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public TempleofPlenty(GameState state)
	{
		super(state);


		// Temple of Plenty enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Temple of Plenty enters the battlefield, scry 1. (Look at the top card of your library. You may put that card on the bottom of your library.)
		this.addAbility(new TempleofPlentyAbility1(state));

		// (T): Add (G) or (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GW)"));
	}
}
