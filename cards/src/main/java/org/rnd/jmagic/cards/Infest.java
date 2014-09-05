package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Infest")
@Types({Type.SORCERY})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Infest extends Card
{
	public Infest(GameState state)
	{
		super(state);

		this.addEffect(ptChangeUntilEndOfTurn(CreaturePermanents.instance(), (-2), (-2), "All creatures get -2/-2 until end of turn."));
	}
}
