package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Damnation")
@Types({Type.SORCERY})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = PlanarChaos.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Damnation extends Card
{
	public Damnation(GameState state)
	{
		super(state);

		this.addEffects(bury(this, org.rnd.jmagic.engine.generators.CreaturePermanents.instance(), "Destroy all creatures. They can't be regenerated."));
	}
}
