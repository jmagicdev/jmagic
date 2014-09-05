package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Goblin War Strike")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Scourge.class, r = Rarity.COMMON), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinWarStrike extends Card
{
	public GoblinWarStrike(GameState state)
	{
		super(state);

		// Goblin War Strike deals damage equal to the number of Goblins you
		// control to target player.
		SetGenerator amount = Count.instance(Intersect.instance(HasSubType.instance(SubType.GOBLIN), ControlledBy.instance(You.instance())));
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(spellDealDamage(amount, target, "Goblin War Strike deals damage equal to the number of Goblins you control to target player."));
	}
}
