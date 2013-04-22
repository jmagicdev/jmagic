package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghostform")
@Types({Type.SORCERY})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Ghostform extends Card
{
	public Ghostform(GameState state)
	{
		super(state);

		// Up to two target creatures are unblockable this turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "up to two target creatures");
		target.setNumber(0, 2);
		this.addEffect(createFloatingEffect("Up to two target creatures are unblockable this turn.", unblockable(targetedBy(target))));
	}
}
