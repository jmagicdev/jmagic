package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Recoil")
@Types({Type.INSTANT})
@ManaCost("1UB")
@Printings({@Printings.Printed(ex = Expansion.INVASION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class Recoil extends Card
{
	public Recoil(GameState state)
	{
		super(state);

		Target target = this.addTarget(Permanents.instance(), "target permanent");
		this.addEffect(bounce(targetedBy(target), "Return target permanent to its owner's hand."));
		this.addEffect(discardCards(OwnerOf.instance(targetedBy(target)), 1, "Then that player discards a card."));
	}
}
