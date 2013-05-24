package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arrows of Justice")
@Types({Type.INSTANT})
@ManaCost("2(R/W)")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class ArrowsofJustice extends Card
{
	public ArrowsofJustice(GameState state)
	{
		super(state);

		// Arrows of Justice deals 4 damage to target attacking or blocking
		// creature.
		Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), Union.instance(Attacking.instance(), Blocking.instance())), "target attacking or blocking creature");
		this.addEffect(spellDealDamage(4, targetedBy(target), "Arrows of Justice deals 4 damage to target attacking or blocking creature."));
	}
}
