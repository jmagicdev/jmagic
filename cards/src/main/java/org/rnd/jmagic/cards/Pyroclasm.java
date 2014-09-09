package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pyroclasm")
@Types({Type.SORCERY})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class Pyroclasm extends Card
{
	public Pyroclasm(GameState state)
	{
		super(state);

		this.addEffect(spellDealDamage(2, CreaturePermanents.instance(), "Pyroclasm deals 2 damage to each creature."));
	}
}
