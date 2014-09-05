package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Murder")
@Types({Type.INSTANT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Murder extends Card
{
	public Murder(GameState state)
	{
		super(state);

		// Destroy target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(destroy(target, "Destroy target creature."));
	}
}
