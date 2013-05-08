package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burning Oil")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BurningOil extends Card
{
	public BurningOil(GameState state)
	{
		super(state);

		// Burning Oil deals 3 damage to target attacking or blocking creature.
		SetGenerator creatures = Union.instance(Attacking.instance(), Blocking.instance());
		SetGenerator target = targetedBy(this.addTarget(creatures, "target attacking or blocking creature"));
		this.addEffect(spellDealDamage(3, target, "Burning Oil deals 3 damage to target attacking or blocking creature."));

		// Flashback (3)(W) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(3)(W)"));
	}
}
