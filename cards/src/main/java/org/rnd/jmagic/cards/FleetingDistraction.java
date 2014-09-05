package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Fleeting Distraction")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON), @Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class FleetingDistraction extends Card
{
	public FleetingDistraction(GameState state)
	{
		super(state);

		// Target creature gets -1/-0 until end of turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), -1, -0, "Target creature gets -1/-0 until end of turn."));

		// Draw a card.
		this.addEffect(drawACard());
	}
}
