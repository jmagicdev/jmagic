package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shatter")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class Shatter extends Card
{
	public Shatter(GameState state)
	{
		super(state);

		Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");
		this.addEffect(destroy(targetedBy(target), "Destroy target artifact."));
	}
}
