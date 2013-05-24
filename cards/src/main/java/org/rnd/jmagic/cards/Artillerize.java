package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Artillerize")
@Types({Type.INSTANT})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Artillerize extends Card
{
	public Artillerize(GameState state)
	{
		super(state);

		// As an additional cost to cast Artillerize, sacrifice an artifact or
		// creature.
		this.addCost(sacrifice(You.instance(), 1, HasType.instance(Type.ARTIFACT, Type.CREATURE), "Sacrifice an artifact or creature"));

		// Artillerize deals 5 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(5, target, "Artillerize deals 5 damage to target creature or player."));
	}
}
