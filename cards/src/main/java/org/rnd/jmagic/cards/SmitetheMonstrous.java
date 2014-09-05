package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Smite the Monstrous")
@Types({Type.INSTANT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SmitetheMonstrous extends Card
{
	public SmitetheMonstrous(GameState state)
	{
		super(state);

		// Destroy target creature with power 4 or greater.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(4, null))), "target creature with power 4 or greater"));

		this.addEffect(destroy(target, "Destroy target creature with power 4 or greater."));
	}
}
