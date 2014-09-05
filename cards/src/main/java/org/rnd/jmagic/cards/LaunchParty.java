package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Launch Party")
@Types({Type.INSTANT})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class LaunchParty extends Card
{
	public LaunchParty(GameState state)
	{
		super(state);

		// As an additional cost to cast Launch Party, sacrifice a creature.
		this.addCost(sacrificeACreature());

		// Destroy target creature. Its controller loses 2 life.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(destroy(target, "Destroy target creature."));
		this.addEffect(loseLife(ControllerOf.instance(target), 2, "Its controller loses 2 life."));
	}
}
