package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Temple of Mystery")
@Types({Type.LAND})
@ColorIdentity({})
public final class TempleofMystery extends Card
{
	public static final class TempleofMysteryAbility1 extends EventTriggeredAbility
	{
		public TempleofMysteryAbility1(GameState state)
		{
			super(state, "When Temple of Mystery enters the battlefield, scry 1.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public TempleofMystery(GameState state)
	{
		super(state);

		// Temple of Mystery enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Temple of Mystery enters the battlefield, scry 1. (Look at the
		// top card of your library. You may put that card on the bottom of your
		// library.)
		this.addAbility(new TempleofMysteryAbility1(state));

		// {T}: Add {G} or {U} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GU)"));
	}
}
