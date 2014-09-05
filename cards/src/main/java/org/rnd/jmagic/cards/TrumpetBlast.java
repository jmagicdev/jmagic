package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Trumpet Blast")
@Types({Type.INSTANT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasDestiny.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TrumpetBlast extends Card
{
	public TrumpetBlast(GameState state)
	{
		super(state);

		this.addEffect(ptChangeUntilEndOfTurn(Attacking.instance(), (+2), (+0), "Attacking creatures get +2/+0 until end of turn."));
	}
}
