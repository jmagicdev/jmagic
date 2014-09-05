package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Grasp of Darkness")
@Types({Type.INSTANT})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GraspofDarkness extends Card
{
	public GraspofDarkness(GameState state)
	{
		super(state);

		// Target creature gets -4/-4 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, -4, -4, "Target creature gets -4/-4 until end of turn."));
	}
}
