package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Temple of Enlightenment")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class TempleofEnlightenment extends Card
{
	public static final class TempleofEnlightenmentAbility1 extends EventTriggeredAbility
	{
		public TempleofEnlightenmentAbility1(GameState state)
		{
			super(state, "When Temple of Enlightenment enters the battlefield, scry 1.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public TempleofEnlightenment(GameState state)
	{
		super(state);

		// Temple of Enlightenment enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Temple of Enlightenment enters the battlefield, scry 1. (Look at
		// the top card of your library. You may put that card on the bottom of
		// your library.)
		this.addAbility(new TempleofEnlightenmentAbility1(state));

		// {T}: Add {W} or {U} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WU)"));
	}
}
