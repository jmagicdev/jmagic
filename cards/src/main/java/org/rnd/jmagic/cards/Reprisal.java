package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Reprisal")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = JourneyIntoNyx.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Alliances.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Reprisal extends Card
{
	public Reprisal(GameState state)
	{
		super(state);

		// Destroy target creature with power 4 or greater. It can't be
		// regenerated.
		SetGenerator target = targetedBy(this.addTarget(HasPower.instance(Between.instance(4, null)), "target creature with power 4 or greater"));
		this.addEffects(bury(this, target, "Destroy target creature with power 4 or greater. It can't be regenerated."));
	}
}
