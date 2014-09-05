package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tribal Flames")
@Types({Type.SORCERY})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.SPECIAL), @Printings.Printed(ex = Invasion.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TribalFlames extends Card
{
	public TribalFlames(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(Domain.instance(You.instance()), targetedBy(target), "Tribal Flames deals X damage to target creature or player, where X is the number of basic land types among lands you control."));
	}
}
