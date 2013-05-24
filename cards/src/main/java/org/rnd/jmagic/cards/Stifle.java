package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stifle")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Stifle extends Card
{
	public Stifle(GameState state)
	{
		super(state);

		// Counter target activated or triggered ability.
		SetGenerator target = targetedBy(this.addTarget(AbilitiesOnTheStack.instance(), "Counter target activated or triggered ability."));
		this.addEffect(counter(target, "Counter target activated or triggered ability."));
	}
}
