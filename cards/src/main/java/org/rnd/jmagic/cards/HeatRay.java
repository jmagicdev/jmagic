package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Heat Ray")
@Types({Type.INSTANT})
@ManaCost("XR")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class HeatRay extends Card
{
	public HeatRay(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), targetedBy(target), "Heat Ray deals X damage to target creature."));
	}
}
