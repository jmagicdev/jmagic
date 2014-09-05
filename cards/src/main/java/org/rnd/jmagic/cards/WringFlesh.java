package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wring Flesh")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class WringFlesh extends Card
{
	public WringFlesh(GameState state)
	{
		super(state);

		// Target creature gets -3/-1 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, -3, -1, "Target creature gets -3/-1 until end of turn."));
	}
}
