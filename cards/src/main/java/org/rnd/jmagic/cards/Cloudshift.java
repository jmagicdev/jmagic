package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cloudshift")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Cloudshift extends Card
{
	public Cloudshift(GameState state)
	{
		super(state);

		// Exile target creature you control, then return that card to the
		// battlefield under your control.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));

		EventFactory factory = new EventFactory(BLINK, "Exile target creature you control, then return that card to the battlefield under your control.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.TARGET, target);
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(factory);
	}
}
