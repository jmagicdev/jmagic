package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Biomass Mutation")
@Types({Type.INSTANT})
@ManaCost("X(G/U)(G/U)")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class BiomassMutation extends Card
{
	public BiomassMutation(GameState state)
	{
		super(state);

		// Creatures you control become X/X until end of turn.
		SetGenerator X = ValueOfX.instance(This.instance());
		this.addEffect(createFloatingEffect("Creatures you control become X/X until end of turn.", setPowerAndToughness(CREATURES_YOU_CONTROL, X, X)));
	}
}
