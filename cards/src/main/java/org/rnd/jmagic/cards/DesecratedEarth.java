package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Desecrated Earth")
@Types({Type.SORCERY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DesecratedEarth extends Card
{
	public DesecratedEarth(GameState state)
	{
		super(state);

		Target target = this.addTarget(LandPermanents.instance(), "target land");

		// Destroy target land.
		this.addEffect(destroy(targetedBy(target), "Destroy target land."));

		// Its controller discards a card.
		this.addEffect(discardCards(ControllerOf.instance(targetedBy(target)), 1, "Its controller discards a card."));
	}
}
