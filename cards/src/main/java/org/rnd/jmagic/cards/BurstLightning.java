package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burst Lightning")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BurstLightning extends Card
{
	public BurstLightning(GameState state)
	{
		super(state);

		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "4");
		this.addAbility(ability);

		CostCollection kickerCost = ability.costCollections[0];

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		SetGenerator amount = IfThenElse.instance(ThisSpellWasKicked.instance(kickerCost), numberGenerator(4), numberGenerator(2));
		this.addEffect(spellDealDamage(amount, targetedBy(target), "Burst Lightning deals 2 damage to target creature or player. If Burst Lightning was kicked, it deals 4 damage to that creature or player instead."));
	}
}
