package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lightning Helix")
@Types({Type.INSTANT})
@ManaCost("RW")
@Printings({@Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class LightningHelix extends Card
{

	public LightningHelix(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Lightning Helix deals 3 damage to target creature or player"));
		this.addEffect(gainLife(You.instance(), 3, "and you gain 3 life."));
	}
}
