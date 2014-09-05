package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Untamed Might")
@Types({Type.INSTANT})
@ManaCost("XG")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class UntamedMight extends Card
{
	public UntamedMight(GameState state)
	{
		super(state);

		// Target creature gets +X/+X until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator X = ValueOfX.instance(This.instance());
		this.addEffect(createFloatingEffect("Target creature gets +X/+X until end of turn.", modifyPowerAndToughness(target, X, X)));
	}
}
