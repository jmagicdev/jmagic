package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Pulse Tracker")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.ROGUE})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PulseTracker extends Card
{
	public static final class PulseTrackerAbility0 extends EventTriggeredAbility
	{
		public PulseTrackerAbility0(GameState state)
		{
			super(state, "Whenever Pulse Tracker attacks, each opponent loses 1 life.");
			this.addPattern(whenThisAttacks());
			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), 1, "Each opponent loses 1 life."));
		}
	}

	public PulseTracker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Pulse Tracker attacks, each opponent loses 1 life.
		this.addAbility(new PulseTrackerAbility0(state));
	}
}
