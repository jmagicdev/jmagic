package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Celestial Purge")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.UNCOMMON)})
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
