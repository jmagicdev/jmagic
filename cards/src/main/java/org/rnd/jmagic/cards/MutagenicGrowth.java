package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mutagenic Growth")
@Types({Type.INSTANT})
@ManaCost("(G/P)")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class MutagenicGrowth extends Card
{
	public MutagenicGrowth(GameState state)
	{
		super(state);

		// ((g/p) can be paid with either (G) or 2 life.)

		// Target creature gets +2/+2 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, +2, +2, "Target creature gets +2/+2 until end of turn."));
	}
}
