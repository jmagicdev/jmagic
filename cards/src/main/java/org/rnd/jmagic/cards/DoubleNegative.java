package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Double Negative")
@Types({Type.INSTANT})
@ManaCost("UUR")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class DoubleNegative extends Card
{
	public DoubleNegative(GameState state)
	{
		super(state);

		// Counter up to two target spells.
		Target target = this.addTarget(Spells.instance(), "target up to two target spells");
		target.setNumber(0, 2);

		this.addEffect(counter(targetedBy(target), "Counter up to two target spells."));
	}
}
