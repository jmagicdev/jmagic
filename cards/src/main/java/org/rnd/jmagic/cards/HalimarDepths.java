package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Halimar Depths")
@Types({Type.LAND})
@ColorIdentity({Color.BLUE})
public final class HalimarDepths extends Card
{
	public static final class ETBIndex extends EventTriggeredAbility
	{
		public ETBIndex(GameState state)
		{
			super(state, "When Halimar Depths enters the battlefield, look at the top three cards of your library, then put them back in any order.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory index = new EventFactory(EventType.LOOK_AND_PUT_BACK, "Look at the top three cards of your library, then put them back in any order.");
			index.parameters.put(EventType.Parameter.CAUSE, This.instance());
			index.parameters.put(EventType.Parameter.PLAYER, You.instance());
			index.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(index);
		}
	}

	public HalimarDepths(GameState state)
	{
		super(state);

		// Halimar Depths enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Halimar Depths enters the battlefield, look at the top three
		// cards of your library, then put them back in any order.
		this.addAbility(new ETBIndex(state));

		// (T): Add (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForU(state));
	}
}
