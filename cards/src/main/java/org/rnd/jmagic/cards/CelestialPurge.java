package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Celestial Purge")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Conflux.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class CelestialPurge extends Card
{
	public CelestialPurge(GameState state)
	{
		super(state);

		// Exile target black or red permanent.
		SetGenerator blackAndRedPermanents = Intersect.instance(HasColor.instance(Color.BLACK, Color.RED), Permanents.instance());
		Target target = this.addTarget(blackAndRedPermanents, "target black or red permanent");
		this.addEffect(exile(targetedBy(target), "Exile target black or red permanent."));
	}
}
