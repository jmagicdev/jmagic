package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Lightning Strike")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2015, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.THEROS, r = Rarity.COMMON)})
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
