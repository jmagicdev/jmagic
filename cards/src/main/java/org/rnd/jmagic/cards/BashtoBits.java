package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bash to Bits")
@Types({Type.INSTANT})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Odyssey.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class BashtoBits extends Card
{
	public BashtoBits(GameState state)
	{
		super(state);

		// Destroy target artifact.
		SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
		this.addEffect(destroy(target, "Destroy target artifact."));

		// Flashback (4)(R)(R) (You may cast this card from your graveyard for
		// its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(4)(R)(R)"));
	}
}
