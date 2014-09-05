package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Chandra's Outrage")
@Types({Type.INSTANT})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ChandrasOutrage extends Card
{
	public ChandrasOutrage(GameState state)
	{
		super(state);

		// Chandra's Outrage deals 4 damage to target creature and 2 damage to
		// that creature's controller.
		// TODO : Simultaneous etc etc
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(4, target, "Chandra's Outrage deals 4 damage to target creature"));
		this.addEffect(spellDealDamage(2, ControllerOf.instance(target), "and 2 damage to that creature's controller."));
	}
}
