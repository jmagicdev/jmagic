package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Amulet of Vigor")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class AmuletofVigor extends Card
{
	public static final class AmuletofVigorAbility0 extends EventTriggeredAbility
	{
		public AmuletofVigorAbility0(GameState state)
		{
			super(state, "Whenever a permanent enters the battlefield tapped and under your control, untap it.");

			ZoneChangePattern etbTapped = new SimpleZoneChangePattern(null, Battlefield.instance(), Tapped.instance(), You.instance(), false);
			this.addPattern(etbTapped);

			SetGenerator it = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			this.addEffect(untap(it, "Untap it."));
		}
	}

	public AmuletofVigor(GameState state)
	{
		super(state);

		// Whenever a permanent enters the battlefield tapped and under your
		// control, untap it.
		this.addAbility(new AmuletofVigorAbility0(state));
	}
}
