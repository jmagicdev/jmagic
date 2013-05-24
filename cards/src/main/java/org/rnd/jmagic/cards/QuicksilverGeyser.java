package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quicksilver Geyser")
@Types({Type.INSTANT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class QuicksilverGeyser extends Card
{
	public QuicksilverGeyser(GameState state)
	{
		super(state);

		Target target = this.addTarget(RelativeComplement.instance(Permanents.instance(), LandPermanents.instance()), "up to two nonland permanents");
		target.setNumber(0, 2);

		// Return up to two target nonland permanents to their owners' hands.
		this.addEffect(bounce(targetedBy(target), "Return up to two target nonland permanents to their owners' hands."));
	}
}
