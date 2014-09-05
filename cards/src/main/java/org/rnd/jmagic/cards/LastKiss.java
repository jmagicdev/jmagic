package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Last Kiss")
@Types({Type.INSTANT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class LastKiss extends Card
{
	public LastKiss(GameState state)
	{
		super(state);

		// Last Kiss deals 2 damage to target creature and you gain 2 life.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(spellDealDamage(2, targetedBy(target), "Last Kiss deals 2 damage to target creature"));

		this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
	}
}
