package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rebuild")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = UrzasLegacy.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Rebuild extends Card
{
	public Rebuild(GameState state)
	{
		super(state);

		// Return all artifacts to their owners' hands.
		this.addEffect(bounce(ArtifactPermanents.instance(), "Return all artifacts to their owners' hands."));

		// Cycling (2) ((2), Discard this card: Draw a card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)"));
	}
}
