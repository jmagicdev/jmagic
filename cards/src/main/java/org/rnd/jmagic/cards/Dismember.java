package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dismember")
@Types({Type.INSTANT})
@ManaCost("1(B/P)(B/P)")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Dismember extends Card
{
	public Dismember(GameState state)
	{
		super(state);

		// ((b/p) can be paid with either (B) or 2 life.)

		// Target creature gets -5/-5 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, -5, -5, "Target creature gets -5/-5 until end of turn."));
	}
}
