package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Infest")
@Types({Type.SORCERY})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Infest extends Card
{
	public Infest(GameState state)
	{
		super(state);

		this.addEffect(ptChangeUntilEndOfTurn(CreaturePermanents.instance(), (-2), (-2), "All creatures get -2/-2 until end of turn."));
	}
}
