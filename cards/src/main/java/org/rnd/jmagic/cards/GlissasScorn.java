package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Glissa's Scorn")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GlissasScorn extends Card
{
	public GlissasScorn(GameState state)
	{
		super(state);

		// Destroy target artifact. Its controller loses 1 life.
		SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
		this.addEffect(destroy(target, "Destroy target artifact."));
		this.addEffect(loseLife(ControllerOf.instance(target), 1, "Its controller loses 1 life."));
	}
}
