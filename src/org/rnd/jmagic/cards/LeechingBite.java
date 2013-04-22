package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Leeching Bite")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class LeechingBite extends Card
{
	public LeechingBite(GameState state)
	{
		super(state);

		// Target creature gets +1/+1 until end of turn. Another target creature
		// gets -1/-1 until end of turn.
		SetGenerator targetOne = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(targetOne, +1, +1, "Target creature gets +1/+1 until end of turn."));
		SetGenerator targetTwo = targetedBy(this.addTarget(CreaturePermanents.instance(), "another target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(targetTwo, -1, -1, "Another target creature gets -1/-1 until end of turn."));
	}
}
