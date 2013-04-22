package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chandra's Fury")
@Types({Type.INSTANT})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ChandrasFury extends Card
{
	public ChandrasFury(GameState state)
	{
		super(state);

		// Chandra's Fury deals 4 damage to target player and 1 damage to each
		// creature that player controls.
		Target target = this.addTarget(Players.instance(), "target player");
		SetGenerator targetPlayer = targetedBy(target);
		this.addEffect(spellDealDamage(4, targetPlayer, "Chandra's Fury deals 4 damage to target player"));

		SetGenerator creaturesThatPlayerControls = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(targetPlayer));
		this.addEffect(spellDealDamage(1, creaturesThatPlayerControls, "and 1 damage to each creature that player controls."));
	}
}
