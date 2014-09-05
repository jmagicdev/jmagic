package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vision Skeins")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Dissension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class VisionSkeins extends Card
{
	public VisionSkeins(GameState state)
	{
		super(state);

		this.addEffect(drawCards(org.rnd.jmagic.engine.generators.Players.instance(), 2, "Each player draws two cards."));
	}
}
