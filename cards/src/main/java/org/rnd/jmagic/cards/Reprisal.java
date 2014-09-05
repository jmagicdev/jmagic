package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reprisal")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.JOURNEY_INTO_NYX, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ALLIANCES, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Reprisal extends Card
{
	public Reprisal(GameState state)
	{
		super(state);


		// Destroy target creature with power 4 or greater. It can't be regenerated.
		SetGenerator target = targetedBy(this.addTarget(HasPower.instance(Between.instance(4, null)), "target creature with power 4 or greater"));
		this.addEffects(bury(this, target, "Destroy target creature with power 4 or greater. It can't be regenerated."));
	}
}
