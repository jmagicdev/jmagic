package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Temple of Abandon")
@Types({Type.LAND})
@ColorIdentity({Color.RED, Color.GREEN})
public final class TempleofAbandon extends Card
{
	public static final class TempleofAbandonAbility1 extends EventTriggeredAbility
	{
		public TempleofAbandonAbility1(GameState state)
		{
			super(state, "When Temple of Abandon enters the battlefield, scry 1.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public TempleofAbandon(GameState state)
	{
		super(state);

		// Temple of Abandon enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Temple of Abandon enters the battlefield, scry 1. (Look at the
		// top card of your library. You may put that card on the bottom of your
		// library.)
		this.addAbility(new TempleofAbandonAbility1(state));

		// {T}: Add {R} or {G} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(RG)"));
	}
}
