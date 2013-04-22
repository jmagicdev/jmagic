package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Frightful Delusion")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class FrightfulDelusion extends Card
{
	public FrightfulDelusion(GameState state)
	{
		super(state);

		// Counter target spell unless its controller pays (1). That player
		// discards a card.
		Target target = this.addTarget(Spells.instance(), "target spell");
		this.addEffect(counterTargetUnlessControllerPays("(1)", target));

		this.addEffect(discardCards(ControllerOf.instance(targetedBy(target)), 1, "That player discards a card."));
	}
}
