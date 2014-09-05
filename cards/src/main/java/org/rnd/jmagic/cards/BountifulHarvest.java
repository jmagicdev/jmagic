package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bountiful Harvest")
@Types({Type.SORCERY})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BountifulHarvest extends Card
{
	public BountifulHarvest(GameState state)
	{
		super(state);

		// You gain 1 life for each land you control.
		SetGenerator youControl = ControlledBy.instance(You.instance());
		SetGenerator lifeAmount = Count.instance(Intersect.instance(LandPermanents.instance(), youControl));
		this.addEffect(gainLife(You.instance(), lifeAmount, "You gain 1 life for each land you control."));
	}
}
