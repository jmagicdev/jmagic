package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Fling")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Stronghold.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Fling extends Card
{
	public Fling(GameState state)
	{
		super(state);

		// As an additional cost to cast Fling, sacrifice a creature.
		EventFactory sac = sacrificeACreature();
		this.addCost(sac);

		// Fling deals damage equal to the sacrificed creature's power to target
		// creature or player.
		SetGenerator amount = PowerOf.instance(OldObjectOf.instance(CostResult.instance(sac)));
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(amount, target, "Fling deals damage equal to the sacrificed creature's power to target creature or player."));
	}
}
