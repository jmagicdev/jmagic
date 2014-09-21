package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Altar of the Brood")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({})
public final class AltaroftheBrood extends Card
{
	public static final class AltaroftheBroodAbility0 extends EventTriggeredAbility
	{
		public AltaroftheBroodAbility0(GameState state)
		{
			super(state, "Whenever another permanent enters the battlefield under your control, each opponent puts the top card of his or her library into his or her graveyard.");

			SimpleZoneChangePattern drop = new SimpleZoneChangePattern(null, Battlefield.instance(), null, You.instance(), false);
			this.addPattern(drop);

			this.addEffect(millCards(OpponentsOf.instance(You.instance()), 1, ""));
		}
	}

	public AltaroftheBrood(GameState state)
	{
		super(state);

		// Whenever another permanent enters the battlefield under your control,
		// each opponent puts the top card of his or her library into his or her
		// graveyard.
		this.addAbility(new AltaroftheBroodAbility0(state));
	}
}
