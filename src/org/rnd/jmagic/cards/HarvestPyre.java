package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Harvest Pyre")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class HarvestPyre extends Card
{
	public HarvestPyre(GameState state)
	{
		super(state);

		SetGenerator X = ValueOfX.instance(This.instance());

		// As an additional cost to cast Harvest Pyre, exile X cards from your
		// graveyard.
		EventFactory cost = exile(You.instance(), InZone.instance(GraveyardOf.instance(You.instance())), X, "exile X cards from your graveyard");
		cost.usesX();
		this.addCost(cost);

		// Harvest Pyre deals X damage to target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(X, target, "Harvest Pyre deals X damage to target creature."));
	}
}
