package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Savage Surge")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class SavageSurge extends Card
{
	public SavageSurge(GameState state)
	{
		super(state);

		// Target creature gets +2/+2 until end of turn. Untap that creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, +2, +2, "Target creature gets +2/+2 until end of turn."));
		this.addEffect(untap(target, "Untap that creature."));
	}
}
