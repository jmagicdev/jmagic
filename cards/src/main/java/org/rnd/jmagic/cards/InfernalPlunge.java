package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Infernal Plunge")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class InfernalPlunge extends Card
{
	public InfernalPlunge(GameState state)
	{
		super(state);

		// As an additional cost to cast Infernal Plunge, sacrifice a creature.
		this.addCost(sacrificeACreature());

		// Add (R)(R)(R) to your mana pool.
		this.addEffect(addManaToYourManaPoolFromSpell("(R)(R)(R)"));
	}
}
