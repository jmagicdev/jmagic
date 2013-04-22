package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Smash to Smithereens")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SmashtoSmithereens extends Card
{
	public SmashtoSmithereens(GameState state)
	{
		super(state);

		// Destroy target artifact. Smash to Smithereens deals 3 damage to that
		// artifact's controller.
		SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
		this.addEffect(destroy(target, "Destroy target artifact."));
		this.addEffect(spellDealDamage(3, ControllerOf.instance(target), "Smash to Smithereens deals 3 damage to that artifact's controller."));
	}
}
