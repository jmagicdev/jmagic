package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ancient Grudge")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
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
