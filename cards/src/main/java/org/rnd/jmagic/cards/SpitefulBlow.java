package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spiteful Blow")
@Types({Type.SORCERY})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class SpitefulBlow extends Card
{
	public SpitefulBlow(GameState state)
	{
		super(state);

		// Destroy target creature and target land.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator target2 = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
		this.addEffect(destroy(Union.instance(target, target2), "Destroy target creature and target land."));
	}
}
