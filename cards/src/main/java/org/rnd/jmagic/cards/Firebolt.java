package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Firebolt")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Odyssey.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Firebolt extends Card
{
	public Firebolt(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(2, targetedBy(target), "Firebolt deals 2 damage to target creature or player."));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(4)(R)"));
	}
}
