package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Peel from Reality")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class PeelfromReality extends Card
{
	public PeelfromReality(GameState state)
	{
		super(state);

		// Return target creature you control and target creature you don't
		// control to their owners' hands.
		SetGenerator targetYours = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
		SetGenerator creaturesYouDontControl = RelativeComplement.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance()));
		SetGenerator targetOther = targetedBy(this.addTarget(creaturesYouDontControl, "target creature you don't control"));
		this.addEffect(bounce(Union.instance(targetYours, targetOther), "Return target creature you control and target creature you don't control to their owners' hands."));
	}
}
