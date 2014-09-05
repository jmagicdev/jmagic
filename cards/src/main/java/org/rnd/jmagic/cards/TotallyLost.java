package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Totally Lost")
@Types({Type.INSTANT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class TotallyLost extends Card
{
	public TotallyLost(GameState state)
	{
		super(state);

		// Put target nonland permanent on top of its owner's library.
		SetGenerator restriction = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
		SetGenerator target = targetedBy(this.addTarget(restriction, "target nonland permanent"));
		this.addEffect(putOnTopOfLibrary(target, "Put target nonland permanent on top of its owner's library."));
	}
}
