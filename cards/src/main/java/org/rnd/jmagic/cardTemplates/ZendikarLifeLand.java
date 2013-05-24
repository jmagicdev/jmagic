package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

abstract public class ZendikarLifeLand extends Card
{
	public static final class LandLife extends EventTriggeredAbility
	{
		private String landName;

		public LandLife(GameState state, String landName)
		{
			super(state, "When " + landName + " enters the battlefield, you gain 1 life.");
			this.landName = landName;

			this.addPattern(whenThisEntersTheBattlefield());

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}

		@Override
		public LandLife create(Game game)
		{
			return new LandLife(game.physicalState, this.landName);
		}
	}

	public ZendikarLifeLand(GameState state, String mana)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		this.addAbility(new LandLife(state, this.getName()));

		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, mana));
	}
}
