package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dream Twist")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class DreamTwist extends Card
{
	public DreamTwist(GameState state)
	{
		super(state);

		// Target player puts the top three cards of his or her library into his
		// or her graveyard.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(millCards(target, 3, "Target player puts the top three cards of his or her library into his or her graveyard."));

		// Flashback (1)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(1)(U)"));
	}
}
