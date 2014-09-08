package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ice")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Apocalypse.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Ice extends Card
{
	public Ice(GameState state)
	{
		super(state);

		// Tap target permanent.
		SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
		this.addEffect(tap(target, "Tap target permanent."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
