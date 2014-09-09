package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Temple of Triumph")
@Types({Type.LAND})
@ColorIdentity({})
public final class TempleofTriumph extends Card
{
	public static final class TempleofTriumphAbility1 extends EventTriggeredAbility
	{
		public TempleofTriumphAbility1(GameState state)
		{
			super(state, "When Temple of Triumph enters the battlefield, scry 1.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public TempleofTriumph(GameState state)
	{
		super(state);

		// Temple of Triumph enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Temple of Triumph enters the battlefield, scry 1. (Look at the
		// top card of your library. You may put that card on the bottom of your
		// library.)
		this.addAbility(new TempleofTriumphAbility1(state));

		// {T}: Add {R} or {W} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(RW)"));
	}
}
