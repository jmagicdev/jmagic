package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Time Warp")
@Types({Type.SORCERY})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.MYTHIC), @Printings.Printed(ex = Starter1999.class, r = Rarity.RARE), @Printings.Printed(ex = Tempest.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class TimeWarp extends Card
{
	public TimeWarp(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(takeExtraTurns(targetedBy(target), 1, "Target player takes an extra turn after this one."));
	}
}
