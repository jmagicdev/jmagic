package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Disorient")
@Types({Type.INSTANT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Disorient extends Card
{
	public Disorient(GameState state)
	{
		super(state);

		// Target creature
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		// gets -7/-0 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (-7), (-0), "Target creature gets -7/-0 until end of turn."));
	}
}
