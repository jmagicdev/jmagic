package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rites of Reaping")
@Types({Type.SORCERY})
@ManaCost("4BG")
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class RitesofReaping extends Card
{
	public RitesofReaping(GameState state)
	{
		super(state);

		// Target creature gets +3/+3 until end of turn. Another target creature
		// gets -3/-3 until end of turn.

		Target target1 = this.addTarget(CreaturePermanents.instance(), "target creature to get +3/+3");
		target1.restrictFromLaterTargets = true;

		Target target2 = this.addTarget(CreaturePermanents.instance(), "target creature to get -3/-3");

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target1), +3, +3, "Target creature gets +3/+3 until end of turn."));
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target2), -3, -3, "Another target creature gets -3/-3 until end of turn."));
	}
}
