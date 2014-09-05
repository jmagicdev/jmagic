package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gut Shot")
@Types({Type.INSTANT})
@ManaCost("(R/P)")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GutShot extends Card
{
	public GutShot(GameState state)
	{
		super(state);

		// ((r/p) can be paid with either (R) or 2 life.)

		// Gut Shot deals 1 damage to target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		this.addEffect(spellDealDamage(1, target, "Gut Shot deals 1 damage to target creature or player."));
	}
}
