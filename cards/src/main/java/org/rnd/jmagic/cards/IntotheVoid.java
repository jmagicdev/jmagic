package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Into the Void")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class IntotheVoid extends Card
{
	public IntotheVoid(GameState state)
	{
		super(state);

		// Return up to two target creatures to their owners' hands.
		Target target = this.addTarget(CreaturePermanents.instance(), "up to two target creatures");
		target.setNumber(0, 2);
		this.addEffect(bounce(targetedBy(target), "Return up to two target creatures to their owners' hands."));
	}
}
