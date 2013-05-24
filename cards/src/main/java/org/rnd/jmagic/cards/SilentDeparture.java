package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silent Departure")
@Types({Type.SORCERY})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SilentDeparture extends Card
{
	public SilentDeparture(GameState state)
	{
		super(state);

		// Return target creature to its owner's hand.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(bounce(targetedBy(target), "Return target creature to its owner's hand."));

		// Flashback (4)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(4)(U)"));
	}
}
