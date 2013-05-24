package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Think Twice")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ThinkTwice extends Card
{
	public ThinkTwice(GameState state)
	{
		super(state);

		this.addEffect(drawACard());

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(2)(U)"));
	}
}
