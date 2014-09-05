package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Temple of Silence")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.THEROS, r = Rarity.RARE)})
@ColorIdentity({})
public final class TempleofSilence extends Card
{
	public static final class TempleofSilenceAbility1 extends EventTriggeredAbility
	{
		public TempleofSilenceAbility1(GameState state)
		{
			super(state, "When Temple of Silence enters the battlefield, scry 1.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public TempleofSilence(GameState state)
	{
		super(state);


		// Temple of Silence enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Temple of Silence enters the battlefield, scry 1. (Look at the top card of your library. You may put that card on the bottom of your library.)
		this.addAbility(new TempleofSilenceAbility1(state));

		// {T}: Add {W} or {B} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WB)"));
	}
}
