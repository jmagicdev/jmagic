package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Khalni Garden")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class KhalniGarden extends Card
{
	public static final class SpawnPlant extends EventTriggeredAbility
	{
		public SpawnPlant(GameState state)
		{
			super(state, "When Khalni Garden enters the battlefield, put a 0/1 green Plant creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 0, 1, "Put a 0/1 green Plant creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.PLANT);
			this.addEffect(token.getEventFactory());
		}
	}

	public KhalniGarden(GameState state)
	{
		super(state);

		// Khalni Garden enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Khalni Garden enters the battlefield, put a 0/1 green Plant
		// creature token onto the battlefield.
		this.addAbility(new SpawnPlant(state));

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
	}
}
