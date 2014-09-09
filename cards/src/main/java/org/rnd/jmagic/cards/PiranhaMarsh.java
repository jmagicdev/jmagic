package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Piranha Marsh")
@Types({Type.LAND})
@ColorIdentity({Color.BLACK})
public final class PiranhaMarsh extends Card
{
	public static final class ETBLoseLife extends EventTriggeredAbility
	{
		public ETBLoseLife(GameState state)
		{
			super(state, "When Piranha Marsh enters the battlefield, target player loses 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(loseLife(targetedBy(target), 1, "Target player loses 1 life."));
		}
	}

	public PiranhaMarsh(GameState state)
	{
		super(state);

		// Piranha Marsh enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Piranha Marsh enters the battlefield, target player loses 1
		// life.
		this.addAbility(new ETBLoseLife(state));

		// (T): Add (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForB(state));
	}
}
