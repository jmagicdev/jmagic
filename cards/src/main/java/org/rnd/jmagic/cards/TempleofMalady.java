package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Temple of Malady")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class TempleofMalady extends Card
{
	public static final class TempleofMaladyAbility1 extends EventTriggeredAbility
	{
		public TempleofMaladyAbility1(GameState state)
		{
			super(state, "When Temple of Malady enters the battlefield, scry 1.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public TempleofMalady(GameState state)
	{
		super(state);

		// Temple of Malady enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Temple of Malady enters the battlefield, scry 1. (Look at the
		// top card of your library. You may put that card on the bottom of your
		// library.)
		this.addAbility(new TempleofMaladyAbility1(state));

		// {T}: Add {B} or {G} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BG)"));
	}
}
