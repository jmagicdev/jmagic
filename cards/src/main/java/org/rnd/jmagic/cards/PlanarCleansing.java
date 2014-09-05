package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Planar Cleansing")
@Types({Type.SORCERY})
@ManaCost("3WWW")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class PlanarCleansing extends Card
{
	public PlanarCleansing(GameState state)
	{
		super(state);

		SetGenerator nonlandPermanents = RelativeComplement.instance(Permanents.instance(), LandPermanents.instance());
		this.addEffect(destroy(nonlandPermanents, "Destroy all nonland permanents."));
	}
}
