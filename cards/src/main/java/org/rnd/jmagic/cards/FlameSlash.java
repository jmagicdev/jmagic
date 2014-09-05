package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Flame Slash")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FlameSlash extends Card
{
	public FlameSlash(GameState state)
	{
		super(state);

		// Flame Slash deals 4 damage to target creature.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(spellDealDamage(4, targetedBy(target), "Flame Slash deals 4 damage to target creature."));
	}
}
