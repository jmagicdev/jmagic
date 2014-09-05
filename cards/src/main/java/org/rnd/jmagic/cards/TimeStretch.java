package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Time Stretch")
@Types({Type.SORCERY})
@ManaCost("8UU")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Odyssey.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class TimeStretch extends Card
{
	public TimeStretch(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(takeExtraTurns(targetedBy(target), 2, "Target player takes two extra turns after this one."));
	}
}
