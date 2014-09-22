package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ground Assault")
@Types({Type.SORCERY})
@ManaCost("RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class GroundAssault extends Card
{
	public GroundAssault(GameState state)
	{
		super(state);

		// Ground Assault deals damage to target creature equal to the number of
		// lands you control.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(spellDealDamage(Count.instance(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance()))), target, "Ground Assault deals damage to target creature equal to the number of lands you control."));
	}
}
