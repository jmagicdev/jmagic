package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mindculling")
@Types({Type.SORCERY})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Mindculling extends Card
{
	public Mindculling(GameState state)
	{
		super(state);

		// You draw two cards and target opponent discards two cards.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		this.addEffect(drawCards(You.instance(), 2, "You draw two cards"));
		this.addEffect(discardCards(target, 2, "and target opponent discards two cards."));
	}
}
