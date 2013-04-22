package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lost in the Mist")
@Types({Type.INSTANT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class LostintheMist extends Card
{
	public LostintheMist(GameState state)
	{
		super(state);

		// Counter target spell.
		SetGenerator targetSpell = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		this.addEffect(counter(targetSpell, "Counter target spell."));

		// Return target permanent to its owner's hand.
		SetGenerator targetPermanent = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
		this.addEffect(bounce(targetPermanent, "Return target permanent to its owner's hand."));
	}
}
