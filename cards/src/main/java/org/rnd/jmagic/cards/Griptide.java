package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Griptide")
@Types({Type.INSTANT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Griptide extends Card
{
	public Griptide(GameState state)
	{
		super(state);

		// Put target creature on top of its owner's library.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		this.addEffect(putOnTopOfLibrary(target, "Put target creature on top of its owner's library."));
	}
}
