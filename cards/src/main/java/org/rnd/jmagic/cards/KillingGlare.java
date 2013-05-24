package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Killing Glare")
@Types({Type.INSTANT})
@ManaCost("XB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class KillingGlare extends Card
{
	public KillingGlare(GameState state)
	{
		super(state);

		// Destroy target creature with power X or less.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(null, ValueOfX.instance(This.instance())))), "target creature with power X or less"));
		this.addEffect(destroy(target, "Destroy target creature with power X or less."));
	}
}
