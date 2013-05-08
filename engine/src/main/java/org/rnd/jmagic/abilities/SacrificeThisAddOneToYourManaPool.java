package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

public final class SacrificeThisAddOneToYourManaPool extends ActivatedAbility
{
	public SacrificeThisAddOneToYourManaPool(GameState state)
	{
		super(state, "Sacrifice this creature: Add (1) to your mana pool.");
		this.addCost(sacrificeThis("this creature"));
		this.addEffect(addManaToYourManaPoolFromAbility("(1)"));
	}
}
