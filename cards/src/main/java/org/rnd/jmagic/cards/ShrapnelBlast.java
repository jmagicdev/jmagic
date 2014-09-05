package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Shrapnel Blast")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ShrapnelBlast extends Card
{
	public ShrapnelBlast(GameState state)
	{
		super(state);

		// As an additional cost to cast Shrapnel Blast, sacrifice an artifact.
		this.addCost(sacrifice(You.instance(), 1, HasType.instance(Type.ARTIFACT), "Sacrifice an artifact"));

		// Shrapnel Blast deals 5 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(5, targetedBy(target), "Shrapnel Blast deals 5 damage to target creature or player."));
	}
}
