package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Saving Grasp")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class SavingGrasp extends Card
{
	public SavingGrasp(GameState state)
	{
		super(state);

		// Return target creature you own to your hand.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CREATURES_YOU_CONTROL, OwnedBy.instance(You.instance())), "target creature you own"));
		this.addEffect(bounce(target, "Return target creature you own to your hand."));

		// Flashback (W) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(W)"));
	}
}
