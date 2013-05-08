package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shattering Spree")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ShatteringSpree extends Card
{
	public ShatteringSpree(GameState state)
	{
		super(state);

		// Replicate (R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Replicate(state, "(R)"));

		// Destroy target artifact.
		Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");
		this.addEffect(destroy(targetedBy(target), "Destroy target artifact."));
	}
}
