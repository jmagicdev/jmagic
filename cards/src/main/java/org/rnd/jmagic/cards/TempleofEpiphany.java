package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Temple of Epiphany")
@Types({Type.LAND})
@ColorIdentity({})
public final class TempleofEpiphany extends Card
{
	public static final class TempleofEpiphanyAbility1 extends EventTriggeredAbility
	{
		public TempleofEpiphanyAbility1(GameState state)
		{
			super(state, "When Temple of Epiphany enters the battlefield, scry 1.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public TempleofEpiphany(GameState state)
	{
		super(state);

		// Temple of Epiphany enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Temple of Epiphany enters the battlefield, scry 1. (Look at the
		// top card of your library. You may put that card on the bottom of your
		// library.)
		this.addAbility(new TempleofEpiphanyAbility1(state));

		// {T}: Add {U} or {R} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(UR)"));
	}
}
