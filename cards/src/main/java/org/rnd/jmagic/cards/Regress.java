package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Regress")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Regress extends Card
{
	public Regress(GameState state)
	{
		super(state);

		// Return target permanent to its owner's hand.
		SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
		this.addEffect(bounce(target, "Return target permanent to its owner's hand."));
	}
}
