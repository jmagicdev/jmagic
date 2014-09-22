package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ancient Grudge")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED, Color.GREEN})
public final class AncientGrudge extends Card
{
	public AncientGrudge(GameState state)
	{
		super(state);

		// Destroy target artifact.
		Target t = this.addTarget(ArtifactPermanents.instance(), "target artifact");
		this.addEffect(destroy(targetedBy(t), "Destroy target artifact."));

		// Flashback (G)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(G)"));
	}
}
