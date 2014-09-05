package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Harrowing Journey")
@Types({Type.SORCERY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class HarrowingJourney extends Card
{
	public HarrowingJourney(GameState state)
	{
		super(state);

		// Target player draws three cards and loses 3 life.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(drawCards(target, 3, "Target player draws three cards"));
		this.addEffect(loseLife(target, 3, "and loses 3 life."));
	}
}
