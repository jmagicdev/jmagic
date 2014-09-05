package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Harmonize")
@Types({Type.SORCERY})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = PlanarChaos.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Harmonize extends Card
{
	public Harmonize(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));
	}
}
