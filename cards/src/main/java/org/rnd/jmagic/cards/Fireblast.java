package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fireblast")
@Types({Type.INSTANT})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.VISIONS, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Fireblast extends Card
{
	public Fireblast(GameState state)
	{
		super(state);

		// You may sacrifice two Mountains rather than pay Fireblast's mana
		// cost.
		EventFactory sac = sacrifice(You.instance(), 2, HasSubType.instance(SubType.MOUNTAIN), "Sacrifice two Mountains");
		CostCollection altCost = new CostCollection(CostCollection.TYPE_ALTERNATE, sac);
		this.addAbility(new org.rnd.jmagic.abilities.AlternateCost(state, "You may sacrifice two Mountains rather than pay Fireblast's mana cost.", altCost));

		// Fireblast deals 4 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(4, target, "Fireblast deals 4 damage to target creature or player."));
	}
}
