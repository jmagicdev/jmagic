package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Go for the Throat")
@Types({Type.INSTANT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class GofortheThroat extends Card
{
	public GofortheThroat(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ArtifactPermanents.instance()), "target nonartifact creature"));

		// Destroy target nonartifact creature.
		this.addEffect(destroy(target, "Destroy target nonartifact creature."));
	}
}
