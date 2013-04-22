package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mirran Mettle")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class MirranMettle extends Card
{
	public MirranMettle(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		SetGenerator number = IfThenElse.instance(Metalcraft.instance(), numberGenerator(4), numberGenerator(2));

		// Target creature gets +2/+2 until end of turn.
		//
		// That creature gets +4/+4 until end of turn instead if you control
		// three or more artifacts.
		this.addEffect(ptChangeUntilEndOfTurn(target, number, number, "Target creature gets +2/+2 until end of turn.\n\nThat creature gets +4/+4 until end of turn instead if you control three or more artifacts."));
	}
}
