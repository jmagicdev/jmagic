package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Smelt")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Magic2014CoreSet.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Smelt extends Card
{
	public Smelt(GameState state)
	{
		super(state);

		// Destroy target artifact.
		SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
		this.addEffect(destroy(target, "Destroy target artifact."));
	}
}
