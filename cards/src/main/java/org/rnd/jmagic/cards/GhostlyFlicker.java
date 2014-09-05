package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ghostly Flicker")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class GhostlyFlicker extends Card
{
	public GhostlyFlicker(GameState state)
	{
		super(state);

		// Exile two target artifacts, creatures, and/or lands you control, then
		// return those cards to the battlefield under your control.
		Target target = this.addTarget(Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.ARTIFACT, Type.CREATURE, Type.LAND)), "two target artifacts, creatures, and/or lands you control");
		target.setSingleNumber(numberGenerator(2));

		EventFactory factory = new EventFactory(BLINK, "Exile two target artifacts, creatures, and/or lands you control, then return those cards to the battlefield under your control.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(factory);
	}
}
