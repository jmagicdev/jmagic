package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Radiant Fountain")
@Types({Type.LAND})
@ColorIdentity({})
public final class RadiantFountain extends Card
{
	public static final class RadiantFountainAbility0 extends EventTriggeredAbility
	{
		public RadiantFountainAbility0(GameState state)
		{
			super(state, "When Radiant Fountain enters the battlefield, you gain 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public RadiantFountain(GameState state)
	{
		super(state);

		// When Radiant Fountain enters the battlefield, you gain 2 life.
		this.addAbility(new RadiantFountainAbility0(state));

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
	}
}
