package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Lightning Strike")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Magic2015CoreSet.class, r = Rarity.COMMON), @Printings.Printed(ex = Theros.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class LightningStrike extends Card
{
	public LightningStrike(GameState state)
	{
		super(state);

		// Lightning Strike deals 3 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Lightning Strike deals 3 damage to target creature or player."));
	}
}
