package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Galvanic Blast")
@Types({Type.INSTANT})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class GalvanicBlast extends Card
{
	public GalvanicBlast(GameState state)
	{
		super(state);

		// Galvanic Blast deals 2 damage to target creature or player.

		// Metalcraft \u2014 Galvanic Blast deals 4 damage to that creature or
		// player instead if you control three or more artifacts.

		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		SetGenerator number = IfThenElse.instance(Metalcraft.instance(), numberGenerator(4), numberGenerator(2));
		this.addEffect(spellDealDamage(number, target, "Galvanic Blast deals 2 damage to target creature or player.\n\nMetalcraft \u2014 Galvanic Blast deals 4 damage to that creature or player instead if you control three or more artifacts."));
	}
}
