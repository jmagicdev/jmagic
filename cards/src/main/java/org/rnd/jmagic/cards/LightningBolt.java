package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Lightning Bolt")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class LightningBolt extends Card
{
	public LightningBolt(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Lightning Bolt deals 3 damage to target creature or player."));
	}
}
